package com.gabriel.listatarecompose.view



import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabriel.listatarecompose.componentes.Botao
import com.gabriel.listatarecompose.componentes.CaixaDeTexto
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.gabriel.listatarecompose.R
import com.gabriel.listatarecompose.data.TarefaDao
import com.gabriel.listatarecompose.model.Tarefa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SalvarTarefa(
    navController: NavController,
    tarefaDao: TarefaDao,
    tarefaId: Int? = null // Parâmetro opcional para o ID da tarefa
) {
    // Estados para armazenar os dados da tarefa
    var tituloTarefa by remember { mutableStateOf("") }
    var descricaoTarefa by remember { mutableStateOf("") }
    var prioridadeSelecionada by remember { mutableStateOf(Prioridade.MEDIA) } // Valor padrão como Média
    var imagemSelecionada by remember { mutableStateOf<Any?>(null) } // Estado para armazenar qualquer tipo de imagem (URL ou recurso local)

    // ImageLoader customizado para GIFs, cache de memória e disco
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25) // Cache de memória limitado a 25% do tamanho disponível
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache")) // Diretório para o cache no disco
                .maxSizePercent(0.02) // Cache de disco limitado a 2% do espaço disponível
                .build()
        }
        .components {
            // Verifica a versão do SDK para usar o decoder correto para GIFs
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())  // Usar ImageDecoder para SDK >= 28
            } else {
                add(GifDecoder.Factory())  // Usar GifDecoder para SDK < 28
            }
        }
        .build()

    // LaunchedEffect para carregar os dados da tarefa existente, se houver
    LaunchedEffect(tarefaId) {
        tarefaId?.let { id ->
            val tarefaExistente = tarefaDao.getTarefaById(id)
            tarefaExistente?.let { tarefa ->
                tituloTarefa = tarefa.tarefa ?: ""
                descricaoTarefa = tarefa.descricao ?: ""
                prioridadeSelecionada = when (tarefa.prioridade) {
                    1 -> Prioridade.BAIXA
                    2 -> Prioridade.MEDIA
                    3 -> Prioridade.ALTA
                    else -> Prioridade.MEDIA
                }
                imagemSelecionada = tarefa.imagemUrl // Carregando a imagem da tarefa existente
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (tarefaId != null) "Editar Tarefa" else "Nova Tarefa",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Magenta
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(52.dp))

            // Campos de texto
            CaixaDeTexto(
                value = tituloTarefa,
                onValueChange = { tituloTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Título Tarefa",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            CaixaDeTexto(
                value = descricaoTarefa,
                onValueChange = { descricaoTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 22.dp, 20.dp, 0.dp),
                label = "Descrição",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )

            // Exibindo a imagem selecionada no Card da tarefa
            imagemSelecionada?.let { imagem ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray) // Fundo cinza para placeholder
                ) {
                    // Verifica se a imagem é uma URL ou um recurso local
                    val imageRequest = if (imagem is String) {
                        // Imagem é uma URL
                        ImageRequest.Builder(context)
                            .data(imagem)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error_24)
                            .build()
                    } else {
                        // Imagem é um recurso local
                        ImageRequest.Builder(context)
                            .data(imagem as Int)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error_24)
                            .build()
                    }

                    // Usando Coil para carregar a imagem
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = "Imagem da tarefa",
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop // Recorta a imagem para se ajustar ao tamanho do Box
                    )
                }
            }

            // Quadro de imagens para seleção
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val imagens = listOf(
                    "https://tabulaquadrada.com.br/wp-content/uploads/card-trick-animated-gif-10.gif", // GIF externo
                    "https://cdn.venngage.com/template/thumbnail/small/ba849fdc-1164-44ad-a42c-22df403498d4.webp", // Imagem externa
                    R.drawable.imagem1, // Imagem local PNG
                    R.drawable.imagem2  // Outra imagem local PNG
                )

                imagens.forEach { image ->
                    // Verificação se a imagem é uma URL ou um recurso local
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(image)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error_24)
                            .build(),
                        contentDescription = null,
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                imagemSelecionada = image // Atualiza a imagem selecionada, seja URL ou recurso local
                            }
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Botões de seleção de prioridade
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = prioridadeSelecionada == Prioridade.BAIXA,
                    onClick = { prioridadeSelecionada = Prioridade.BAIXA },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = Color.Green.copy(alpha = 0.6f),
                        selectedColor = Color.Green
                    )
                )
                Text(text = "Baixa")

                RadioButton(
                    selected = prioridadeSelecionada == Prioridade.MEDIA,
                    onClick = { prioridadeSelecionada = Prioridade.MEDIA },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = Color.Yellow.copy(alpha = 0.6f),
                        selectedColor = Color.Yellow
                    )
                )
                Text(text = "Média")

                RadioButton(
                    selected = prioridadeSelecionada == Prioridade.ALTA,
                    onClick = { prioridadeSelecionada = Prioridade.ALTA },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = Color.Red.copy(alpha = 0.6f),
                        selectedColor = Color.Red
                    )
                )
                Text(text = "Alta")
            }

            // Botão de salvar tarefa
            Botao(onClick = {
                if (tituloTarefa.isNotBlank() && descricaoTarefa.isNotBlank()) {
                    val novaTarefa = Tarefa(
                        id = tarefaId ?: 0,
                        tarefa = tituloTarefa,
                        descricao = descricaoTarefa,
                        prioridade = when (prioridadeSelecionada) {
                            Prioridade.BAIXA -> 1
                            Prioridade.MEDIA -> 2
                            Prioridade.ALTA -> 3
                        },
                        imagemUrl = imagemSelecionada?.toString() // Armazena a URL ou recurso local como string
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        if (tarefaId == null) {
                            tarefaDao.insert(novaTarefa)
                        } else {
                            tarefaDao.update(novaTarefa)
                        }
                    }

                    navController.popBackStack()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(20.dp),
                texto = if (tarefaId != null) "Salvar Alterações" else "Criar Tarefa"
            )
        }
    }
}
enum class Prioridade {
    BAIXA, MEDIA, ALTA
}