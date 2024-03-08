package com.cs4520.assignment4.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment4.R

/**
 * This LoginFragment displays the login screen,
 * where the user can enter their username and password
 * to login. If they enter the correct credentials, they
 * are navigated to the ProductListFragment.
 */
class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.login_button)?.setOnClickListener {
            val usernameField = view.findViewById<EditText>(R.id.username_field)
            val passwordField = view.findViewById<EditText>(R.id.password_field)

            // checks if the user has entered the correct credentials
            if (usernameField.text.toString() == "admin"
                && passwordField.text.toString() == "admin") {
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                usernameField.text.clear()
                passwordField.text.clear()
            } else if (usernameField.text.toString().isEmpty()
                || passwordField.text.toString().isEmpty()) {
                Toast.makeText(
                    this.context,
                    "Please enter your username and password!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                    Toast.makeText(
                        this.context,
                        "Invalid credentials, Try again!",
                        Toast.LENGTH_LONG
                    ).show()
                    usernameField.text.clear()
                    passwordField.text.clear()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}