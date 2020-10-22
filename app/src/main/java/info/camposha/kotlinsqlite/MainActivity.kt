package info.camposha.kotlinsqlite

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    internal var dbRecipe = DbRecipe(this)

    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    fun clearEditTexts(){
        idTxt.setText("")
        nameTxt.setText("")
        ingredientTxt.setText("")
        stepTxt.setText("")
        typeTxt.setText("")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val rType = arrayOf("BREAKFSAT", "LUNCH", "DINNER")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,rType)

        typeTxt.adapter = arrayAdapter

        typeTxt.onItemSelectedListener = object :

        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }*/

        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
    }


    fun handleInserts() {
        insertBtn.setOnClickListener {
            if(nameTxt.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill name", Toast.LENGTH_SHORT).show()
            }
            else if(ingredientTxt.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill ingredient", Toast.LENGTH_SHORT).show()
            }
            else if(stepTxt.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill step", Toast.LENGTH_SHORT).show()
            }
            else if(typeTxt.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill type", Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbRecipe.insertData(nameTxt.text.toString(), ingredientTxt.text.toString(),
                            stepTxt.text.toString(), typeTxt.text.toString())
                    clearEditTexts()
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast(e.message.toString())
                }
            }
        }
    }


    fun handleUpdates() {
        updateBtn.setOnClickListener {
            if(idTxt.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill ID to update", Toast.LENGTH_SHORT).show()
            }else {
                try {
                    val isUpdate = dbRecipe.updateData(idTxt.text.toString(),nameTxt.text.toString(),
                            ingredientTxt.text.toString(),stepTxt.text.toString(), typeTxt.text.toString())
                    if (isUpdate == true)
                        showToast("Data Updated Successfully")
                    else
                        showToast("Data Not Updated")
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast(e.message.toString())
                }
            }
        }
    }


    fun handleDeletes(){
        deleteBtn.setOnClickListener {
            if(idTxt.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill ID to update", Toast.LENGTH_SHORT).show()
            }else {
                try {
                    dbRecipe.deleteData(idTxt.text.toString())
                    clearEditTexts()
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast(e.message.toString())
                }
            }
        }
    }


    fun handleViewing() {
        viewBtn.setOnClickListener(
                View.OnClickListener {
                    val res = dbRecipe.allData
                    if (res.count == 0) {
                        showDialog("Error", "No Recipe Found")
                        return@OnClickListener
                    }

                    val buffer = StringBuffer()
                    while (res.moveToNext()) {
                        buffer.append("ID :" + res.getString(0) + "\n")
                        buffer.append("NAME :" + res.getString(1) + "\n")
                        buffer.append("INGREDIENT :" + res.getString(2) + "\n")
                        buffer.append("STEP :" + res.getString(3) + "\n")
                        buffer.append("TYPE :" + res.getString(4) + "\n\n")
                    }
                    showDialog("Recipe Listing", buffer.toString())
                }
        )
    }
}
//end