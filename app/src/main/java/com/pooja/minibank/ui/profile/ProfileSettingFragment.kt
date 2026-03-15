package com.pooja.minibank.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pooja.minibank.core.utils.Constants
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.data.local.pref.TokenManager
import com.pooja.minibank.databinding.FragmentProfileSettingBinding
import com.pooja.minibank.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileSettingFragment : Fragment() {

    private  var _binding: FragmentProfileSettingBinding? =null
    private val binding get() = _binding!!


    @Inject lateinit var pref: PreferenceManager
    @Inject lateinit  var tokenManager: TokenManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        _binding = FragmentProfileSettingBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = pref.getStringPref(Constants.SP_USERNAME)

        binding.tvName.text = Constants.getName(name?:"User")
        binding.tvInitial.text = Constants.getInitialLetter(name?:"User")


        setupDarkMode()
        setupColorChange()
        setupLogout()
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    private fun setupDarkMode() {

        binding.switchDark.isChecked = pref.getBooleanPref("dark_mode")

        binding.switchDark.setOnCheckedChangeListener { _, isChecked ->

            pref.addPref("dark_mode", isChecked)

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupColorChange() {

        binding.colorGreen.setOnClickListener {
            saveColor("#2E7D32")
        }

        binding.colorBlue.setOnClickListener {
            saveColor("#1565C0")
        }

        binding.colorPurple.setOnClickListener {
            saveColor("#6A1B9A")
        }
    }

    private fun saveColor(color: String) {

        pref.addPref("theme_color", color)
        showRestartDialog()
    }

    private fun setupLogout() {

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }


    private fun showLogoutDialog() {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setCancelable(false)
            .setPositiveButton("Logout") { dialog, _ ->

                // Clear user session / token here
                logoutUser()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logoutUser() {

        tokenManager.clearSession()

        pref.addPref("user_logged_in", false)
        pref.addPref("biometric_enabled", false)

        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finishAffinity()
    }

    private fun showRestartDialog() {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Restart App")
            .setMessage("Theme color changed. Restart app to apply changes?")
            .setCancelable(false)
            .setPositiveButton("Restart") { _, _ ->

                val intent = requireActivity().packageManager
                    .getLaunchIntentForPackage(requireActivity().packageName)

                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent!!)

                requireActivity().finishAffinity()
            }
            .setNegativeButton("Later") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}


