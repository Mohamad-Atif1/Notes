package com.example.mypracticeapplication4

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

class MainActivity : AppCompatActivity() {
    lateinit var myText:EditText
    lateinit var plusButton: Button
    lateinit var minButton: Button
    lateinit var theSize:TextView
    lateinit var boldCheck:CheckBox
    lateinit var italicCheck : CheckBox
    lateinit var spinnerColor : Spinner
    lateinit var spinnerFonts : Spinner
    lateinit var lTRbutton : RadioButton
    lateinit var rTLbutton : RadioButton
    lateinit var fileName : EditText
    lateinit var newButton: Button
    lateinit var saveButton: Button
    lateinit var getFileButton: Button
    lateinit var centerButton: RadioButton
    lateinit var spinnerFiles: Spinner
    lateinit var sharedpreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tf:Typeface = Typeface.createFromAsset(assets,"Amiri_Italic.ttf")
        initViews()
        var editor = sharedpreferences.edit()
        editor.clear().commit()
        fun boldText (){
            if (boldCheck.isChecked) {
                myText.setTypeface(tf , Typeface.BOLD)
            }
            else {
                myText.setTypeface(tf , Typeface.NORMAL)

            }
        }


        fillColors()
        fillFonts()
        plusButton.setOnClickListener{ sizePlus() }
        minButton.setOnClickListener{ sizemin() }
        boldCheck.setOnCheckedChangeListener{ buttonView, isChecked ->
            boldText ()
        }
        italicCheck.setOnCheckedChangeListener(){buttonView, isChecked ->
            italicText()

        }


        spinnerFonts.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var tf:Typeface = Typeface.createFromAsset(assets,"Amiri_Italic.ttf")
                when (selectedItem) {
                    " Amiri_Italic  " ->
                        //        myText.setTypeface
                        tf = Typeface.createFromAsset(assets,"Amiri_Italic.ttf")
                    "Amiri_Regular" ->
                        tf = (Typeface.createFromAsset(assets,"Amiri_Regular.ttf"))

                    "ArefRuqaa_Bold"->
                        tf = (Typeface.createFromAsset(assets,"ArefRuqaa_Bold.ttf"))

                    "Cairo_Black"->
                        tf = (Typeface.createFromAsset(assets,"Cairo_Black.ttf"))

                    "Cairo_Bold"->
                        tf = (Typeface.createFromAsset(assets,"Cairo_Bold.ttf"))

                    "ElMessiri_Bold"->
                        tf = (Typeface.createFromAsset(assets,"ElMessiri_Bold.ttf"))

                    "ElMessiri_SemiBold"->
                        tf = (Typeface.createFromAsset(assets,"ElMessiri_SemiBold.ttf"))

                    "Lateef_Regular"->
                        tf = (Typeface.createFromAsset(assets,"Lateef_Regular.ttf"))

                }
                myText.typeface = tf
                if (boldCheck.isChecked) {
                    myText.setTypeface(tf , Typeface.BOLD)
                }
                else {
                    myText.setTypeface(tf , Typeface.NORMAL)

                }
            }

        }
        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedItem = parent?.getItemAtPosition(position).toString()

                when (selectedItem) {
                    "black"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))

                    "blue"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
                    "red"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                    "purple"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.purple))
                    "pink"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.pink))
                    "yellow"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.yellow))
                    "brown"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.brown))
                    "lightBlue" ->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.lightBlue))
                    "green"->
                        myText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))

                }

            }

        }




        rTLbutton.setOnCheckedChangeListener { buttonView, isChecked ->

            textAlign()
        }
        lTRbutton.setOnCheckedChangeListener { buttonView, isChecked ->

            textAlign()
        }
        centerButton.setOnCheckedChangeListener { buttonView, isChecked ->

            textAlign()
        }
        newButton.setOnClickListener(){newButton()}
        saveButton.setOnClickListener(){saveButton()}
        getFileButton.setOnClickListener(){getButton()}





        fillFilesSpinner()
    }

    fun initViews(){
        myText = findViewById(R.id.myText)
        plusButton  = findViewById(R.id.plusButton)
        minButton = findViewById(R.id.minButton)
        theSize = findViewById(R.id.theSize)
        boldCheck = findViewById(R.id.boldCheck)
        italicCheck = findViewById(R.id.italicCheck)
        spinnerColor  = findViewById(R.id.spinnerColor)
        spinnerFonts = findViewById(R.id.spinnerFonts)
        lTRbutton = findViewById(R.id.lTRbutton)
        rTLbutton = findViewById(R.id.rTLbutton)
        fileName = findViewById(R.id.fileName)
        newButton = findViewById(R.id.newButton)
        saveButton = findViewById(R.id.saveButton)
        getFileButton = findViewById(R.id.getFileButton)
        centerButton = findViewById(R.id.centerButton)
        spinnerFiles = findViewById(R.id.spinnerFiles)
        sharedpreferences = this.getSharedPreferences("txtData",Context.MODE_PRIVATE)
        initFileData()

    }
    fun fillColors () {
        val colorsNames = arrayOf("black","blue","red","purple","pink","yellow","brown","lightBlue" , "green" )
        val colorsAdapter:ArrayAdapter<String> = ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item ,colorsNames )
        spinnerColor.adapter = colorsAdapter
    }



    fun fillFonts () {
        val fontsNames = arrayOf(
            " Amiri_Italic  ",
            "Amiri_Regular",
            "ArefRuqaa_Bold",
            "Cairo_Black",
            "Cairo_Bold",
            "ElMessiri_Bold",
            "ElMessiri_SemiBold",
            "Lateef_Regular",
        )

        val  fontsAdapter:ArrayAdapter<String> = ArrayAdapter(this , android.R.layout.simple_spinner_item , fontsNames)
        spinnerFonts.adapter = fontsAdapter
    }

    fun sizePlus(){

        var size:Int = theSize.text.toString().toInt()
        if (size != 108) {
            size++
        }
        myText.textSize = size.toFloat()
        theSize.text = size.toString()



    }

    fun sizemin(){
        var size:Int = theSize.text.toString().toInt()
        if (size != 7 ) {
            size--
        }
        myText.textSize = size.toFloat()
        theSize.text = size.toString()


    }

    /* fun boldText (){
          if (boldCheck.isChecked) {
              myText.setTypeface(null , Typeface.BOLD)
          }
         else {
              myText.setTypeface(null , Typeface.NORMAL)

          }
     } */

    fun italicText () {
        if (italicCheck.isChecked){
            myText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        }
        else {
            myText.paintFlags = Paint.LINEAR_TEXT_FLAG
        }

    }

    fun textAlign() {
        if (lTRbutton.isChecked) {
            myText.gravity = Gravity.START
        } else if (rTLbutton.isChecked) {
            myText.gravity = Gravity.END
        } else {
            myText.gravity = Gravity.CENTER_HORIZONTAL
        }


    }

    fun newButton() {

        myText.setText("")
        lTRbutton.setChecked(true)
        spinnerColor.setSelection(0)
        spinnerFonts.setSelection(0)
        theSize.text = "18"
        myText.textSize = 18F
        boldCheck.setChecked(false)
        italicCheck.setChecked(false)
        fileName.setText("Name")
        myText.requestFocus()  // يخلي الماوس عليها

    }

    private fun saveButton(){
        val stringData = ArrayList<String>()
//        (,spinnerColor.selectedItem,spinnerFonts.selectedItem,theSize.text,fileName.text,
//        LTRbutton.isChecked,RTLbutton.isChecked,boldCheck.isChecked,italicCheck.isChecked)
        stringData.add(myText.text.toString())
        stringData.add(fileName.text.toString())
        stringData.add(spinnerColor.selectedItemPosition.toString())
        stringData.add(spinnerFonts.selectedItemPosition.toString())
        stringData.add(theSize.text.toString())

        val booleanData = ArrayList<Boolean>()
        booleanData.add(lTRbutton.isChecked)
        booleanData.add(rTLbutton.isChecked)
        booleanData.add(boldCheck.isChecked)
        booleanData.add(italicCheck.isChecked)

        val editor = sharedpreferences.edit()
        val gson = Gson()
        editor.putString(fileName.text.toString(),gson.toJson(stringData))
        editor.putString(fileName.text.toString() + "BOOLEAN",gson.toJson(booleanData))
        editor.commit()
        addFile(fileName.text.toString())
        fillFilesSpinner()
    }

    fun getButton(){
        val stringData:ArrayList<String> = getStringDetails()
        val booleanData:ArrayList<Boolean> = getBooleanDetails()
        if(stringData.isNotEmpty() && booleanData.isNotEmpty()) {
            myText.setText(stringData[0])
            spinnerColor.setSelection(stringData[2].toInt())
            spinnerFonts.setSelection(stringData[3].toInt())
            theSize.setText(stringData[4])
            myText.textSize = theSize.text.toString().toFloat()

            lTRbutton.isChecked = booleanData[0]
            rTLbutton.isChecked = booleanData[1]
            boldCheck.isChecked = booleanData[2]
            italicCheck.isChecked = booleanData[3]
        }

    }

    fun fillFilesSpinner(){
        val data:ArrayList<String> = getFile()


        val  filesAdapter:ArrayAdapter<String> = ArrayAdapter(this , android.R.layout.simple_spinner_item , data)

        spinnerFiles.adapter = filesAdapter
        spinnerFiles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                fileName.setText(selectedItem)

            }

        }
    }
    fun getStringDetails():ArrayList<String>{
        val type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
        val gson = Gson()
        val data:ArrayList<String>? = gson.fromJson(sharedpreferences.getString(fileName.text.toString(),null),type)
        if (data != null)
            return data
        else{
            return ArrayList()
        }
    }
    fun getBooleanDetails():ArrayList<Boolean>{
        val type = object : TypeToken<java.util.ArrayList<Boolean?>?>() {}.type
        val gson = Gson()
        val data:ArrayList<Boolean>? = gson.fromJson(sharedpreferences.getString(fileName.text.toString()+"BOOLEAN",null),type)
        if (data != null)
            return data
        else{
            return ArrayList()
        }
    }
    fun initFileData(){
        val Files:ArrayList<String> = getFile()
        val editor = sharedpreferences.edit()
        val gson = Gson()
        editor.putString("FILENAME",gson.toJson(Files))
        editor.commit()

    }
    fun getFile():ArrayList<String>{
        val type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
        val gson = Gson()
        val data:ArrayList<String>? = gson.fromJson(sharedpreferences.getString("FILENAME",null),type)
        if (data != null)
        return data
        else{
            return ArrayList()
        }
    }
    fun addFile( str:String){
        val data:ArrayList<String> = getFile()
        val gson = Gson()
        data.add(str)
        val editor = sharedpreferences.edit()
        editor.remove("FILENAME")
        editor.putString("FILENAME",gson.toJson(data))
        editor.commit()
    }
}
//fun saveButton(){
//    try {
//
//        var strFile:String = Environment.getExternalStorageDirectory().path
//        var file:File = File("$strFile/somechanges")
//        file.mkdir()
//        if (file.exists() && file.isDirectory) {
//            var pw = PrintWriter("$strFile/${fileName.text}.txt")
//            pw.write(myText.text.toString())
//            pw.close()
//            Toast.makeText(this,"saved " ,  Toast.LENGTH_LONG).show()
//
//        }
//        else {
//            Toast.makeText(this,"Not yet " ,  Toast.LENGTH_LONG).show()
//
//        }
//
//    }
//    catch (e:Exception){
//
//        Toast.makeText(this,e.toString() ,  Toast.LENGTH_LONG).show()
//    }
//
//}
//
//fun getButton() {
//    var strpath = Environment.getExternalStorageDirectory().path
//    var fr = FileReader ("$strpath/${fileName.text}.txt")
//    var br = BufferedReader (fr)
//    var strContent = ""
//    var line:String = ""
//    line = br.readLine()
//    myText.setText(line)
//}



























/*

fun Showmassege (M:String){
    val mymassege:Toast = Toast.makeText(this,M,Toast.LENGTH_LONG)
    mymassege.show()
    var designText:TextView = mymassege.view!!.findViewById(android.R.id.message)
    designText.textSize = 30F
}*/