/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 2
    Author: Lai Nghiep Tri
    ID: s3799602
    Created  date: 19/12/2023
    Last modified: 20/12/2023
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */


package com.example.earthlings.presentation.statscreen

import android.graphics.fonts.Font
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.earthlings.model.StatData
import com.example.earthlings.presentation.common.CommonVar
import org.kpropmap.propMapOf


@Composable
@Preview(showBackground = true)
fun StatScreen(isSuperUser: Boolean = true,
               inputData: StatData = StatData(
                   10,
                   12)) {
// Data class to Map
    val statMap = propMapOf(inputData)
    Log.i("PropMap", "$statMap")
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text("Dashboard",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        )
        if (isSuperUser != true) {
            Text(fontSize = 20.sp, text = "Sorry this feature is for super user only")
        } else {
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                item {
                    statMap.forEach {
                        val value = it.value
                        val entry = camelCaseToNormalSpacing(it.key)
                        Column {
                            Row {
                                Text(
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(4.dp),
                                    text = "$entry: "
                                )
                                Text(
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(4.dp),
                                    text = "$value"
                                )
                            }
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    CommonVar.placeHolderImageURL,
                    contentDescription = "Stats Image"
                )
            }
        }
    }
}

//Quick and dirty way to serialize object from element to a map
fun camelCaseToNormalSpacing(text: String): String {
    return text.replace(Regex("([a-z])([A-Z]+)"), "$1 $2")
}