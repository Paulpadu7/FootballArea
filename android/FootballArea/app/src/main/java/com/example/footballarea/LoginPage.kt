package com.example.footballarea

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.footballarea.Adapters.MatchAdapters
class LoginPage : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_page, container, false)
        var loginButton:Button = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_loginPage_to_mainPage,null,
                navOptions {
                    anim {
                        enter = android.R.animator.fade_in
                        exit = android.R.animator.fade_out
                    }
                })
        }
        return view
    }
}