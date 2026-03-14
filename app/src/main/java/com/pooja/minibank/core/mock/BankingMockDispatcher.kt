package com.pooja.minibank.core.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class BankingMockDispatcher : Dispatcher() {
    private val usedKeys = mutableSetOf<String>()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path?: return error500()
        val method = request.method ?: ""

        return when{
            method == "POST" && path == "/auth/login" -> login(request)
            method == "POST" && path == "/auth/otp/verify" -> otpVerify(request)
            method == "GET" && path == "/accounts" -> accounts(request)
            method == "GET" && path.contains("/transaction")-> transactions(request)
            method == "GET" && path == "/beneficiaries" -> beneficiaries(request)
            method == "POST" && path == "/transfers" -> transfer(request)
            else -> MockResponse().setResponseCode(404)
        }
    }

    private fun login(req : RecordedRequest) : MockResponse{
        val body = req.body.readUtf8()
        return if(body.contains("bhargav.suthar") && body.contains("Password@123"))
        {
            ok("""{"sessionId":"sess_89a7c6","otpRequired":true,"maskedPhone":"XXXXXX8943"}""")
        }
        else
        {
            error(401, """{"error":"UNAUTHORIZED","message":"Invalid username or password"}""")

        }
    }

    private fun otpVerify(req: RecordedRequest): MockResponse {
        val body = req.body.readUtf8()
        return if (body.contains("sess_89a7c6") && body.contains("123456")) {
            ok("""{"accessToken":"mock-access-token-123","refreshToken":"mock-refresh-token-987","expiresIn":3600}""")
        } else {
            error(400, """{"error":"INVALID_REQUEST","message":"Wrong OTP"}""")
        }
    }

    private fun accounts(req: RecordedRequest): MockResponse {
        if (!isAuth(req)) return error(401, """{"error":"UNAUTHORIZED"}""")
        return ok("""[
          {"id":"acc_101","name":"Savings Account","maskedNumber":"XXXX8790","currency":"INR","balance":152340.75,"updatedAt":"2026-03-10T06:30:00Z"},
          {"id":"acc_102","name":"Current Account","maskedNumber":"XXXX4321","currency":"INR","balance":80235.40,"updatedAt":"2026-03-09T05:10:00Z"}
        ]""")
    }

    private fun transactions(req: RecordedRequest): MockResponse {
        if (!isAuth(req)) return error(401, """{"error":"UNAUTHORIZED"}""")
        return ok("""{
          "items":[
            {"id":"txn_5001","accountId":"acc_101","type":"DEBIT","amount":1200.25,"currency":"INR","counterparty":"Airtel","timestamp":"2026-03-12T11:10:00Z","status":"SETTLED","narration":"Mobile Bill"},
            {"id":"txn_5002","accountId":"acc_101","type":"CREDIT","amount":45000.00,"currency":"INR","counterparty":"Salary","timestamp":"2026-03-01T05:00:00Z","status":"SETTLED","narration":"Monthly Salary"},
            {"id":"txn_5003","accountId":"acc_101","type":"DEBIT","amount":500.00,"currency":"INR","counterparty":"Swiggy","timestamp":"2026-03-05T13:00:00Z","status":"SETTLED","narration":"Food Order"}
          ],
          "nextPage":null
        }""")
    }

    private fun beneficiaries(req: RecordedRequest): MockResponse {
        if (!isAuth(req)) return error(401, """{"error":"UNAUTHORIZED"}""")
        return ok("""[
          {"id":"ben_301","name":"Rahul Sharma","accountMasked":"XXXX9876","bank":"ABC Bank"},
          {"id":"ben_302","name":"Sneha Kapoor","accountMasked":"XXXX6543","bank":"XYZ Bank"}
        ]""")
    }

    private fun transfer(req: RecordedRequest): MockResponse {
        if (!isAuth(req)) return error(401, """{"error":"UNAUTHORIZED"}""")

        val key = req.getHeader("Idempotency-Key")
            ?: return error(
                400,
                """{"error":"INVALID_REQUEST","message":"Idempotency-Key required"}"""
            )

        if (usedKeys.contains(key)) {
            return error(
                409,
                """{"status":"DUPLICATE","transferId":"trf_8001","message":"Already processed"}"""
            )
        }

        val body = req.body.readUtf8()
        val amount =
            Regex("\"amount\":(\\d+\\.?\\d*)").find(body)?.groupValues?.get(1)?.toDoubleOrNull()
                ?: 0.0

        if (amount > 200000) {
            return error(
                400,
                """{"status":"FAILED","errorCode":"INSUFFICIENT_FUNDS","message":"Insufficient balance"}"""
            )
        }

        usedKeys.add(key)
        return MockResponse().setResponseCode(201)
            .setHeader("Content-Type", "application/json")
            .setBody("""{"transferId":"trf_8001","status":"SUCCESS","referenceNumber":"MBK12345678","processedAt":"2026-03-13T07:30:05Z"}""")
        }

        private fun isAuth(req: RecordedRequest) =
            req.getHeader("Authorization") == "Bearer mock-access-token-123"

        fun ok(json: String) = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(json)

        fun error(code: Int, json: String) = MockResponse()
            .setResponseCode(code)
            .setHeader("Content-Type", "application/json")
            .setBody(json)

        fun error500() = error(500, """{"error":"SERVER_ERROR"}""")

    }
