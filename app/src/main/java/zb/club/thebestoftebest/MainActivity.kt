package zb.club.thebestoftebest

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)







    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.up_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }
