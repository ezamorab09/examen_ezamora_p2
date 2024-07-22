package cl.eduardozamora.ezamora_ef_2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import androidx.room.util.TableInfo
import cl.eduardozamora.ezamora_ef_2024.db.BaseDatos
import cl.eduardozamora.ezamora_ef_2024.entity.Registro
import cl.eduardozamora.ezamora_ef_2024.ui.theme.Ezamoraef2024Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {

    lateinit var db: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            BaseDatos::class.java,
            "mi-basedatos"
        ).build()

        setContent {

            Ezamoraef2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistroForm(
                        modifier = Modifier.padding(innerPadding),
                        db = db

                    )
                }
            }
        }
    }
}

@Composable
fun RegistroForm(modifier: Modifier = Modifier, db: BaseDatos) {
    val scope = rememberCoroutineScope()
    var tipo by remember { mutableStateOf("agua") }
    var medidor by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // Encabezado centrado
        Text(
            text = stringResource(id = R.string.title_registro_medidor),
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        // Elementos alineados a la izquierda
        TextField(
            value = medidor,
            onValueChange = { medidor = it },
            label = { Text(stringResource(id = R.string.label_medidor)) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text(stringResource(id = R.string.label_fecha)) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.tipo_medidor),
            modifier = Modifier.align(Alignment.Start)
        )
        Column(modifier = Modifier.align(Alignment.Start)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipo == "agua",
                    onClick = { tipo = "agua" }
                )
                Image(
                    painter = painterResource(id = R.drawable.agua),
                    contentDescription = stringResource(id = R.string.agua),
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text(stringResource(id = R.string.agua))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipo == "luz",
                    onClick = { tipo = "luz" }
                )
                Image(
                    painter = painterResource(id = R.drawable.bombilla),
                    contentDescription = stringResource(id = R.string.luz),
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text(stringResource(id = R.string.luz))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipo == "gas",
                    onClick = { tipo = "gas" }
                )
                Image(
                    painter = painterResource(id = R.drawable.gas),
                    contentDescription = stringResource(id = R.string.gas),
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text(stringResource(id = R.string.gas))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Botón centrado
        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val icono = when (tipo) {
                        "agua" -> "agua"
                        "luz" -> "bombilla"
                        "gas" -> "gas"
                        else -> "agua"
                    }
                    val registro = Registro(
                        medidor = medidor.toInt(),
                        tipo = tipo,
                        fecha = fecha.toLong(),
                        icono = icono
                    )
                    db.registroDao().insertAll(registro)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(id = R.string.guardar))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegistroFormPreview() {
    Ezamoraef2024Theme {
        RegistroForm(db = Room.inMemoryDatabaseBuilder(
            LocalContext.current,
            BaseDatos::class.java
        ).build())
    }
}