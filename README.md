# SSC0103
Programação Orientada a Objeto

## Autores
- Andrey Cortez Rufino - 11819487
- Francyélio de Jesus Campos Lima - 13676537

## Instruções

Para rodar o programa primeiro compile a database e ligue o servidor

```shell
cd database
mkdir bin
make all
python3 server.py
```

Então em um terminal separado compile e rode o programa em Java
```shell
make all
make run
```

Para conectar ao servidor vá na barra de menu inicial e clique no botão correspondente
o endereço e porta padrão são
```shell
endereço: localhost
porta: 8080
```

Depois disso vá na barra de menu inicial e selecione um arquivo fifa

Pronto! agora você pode buscar, listar todos os jogadores e deletar ou editar um jogador específico

## Bibliotecas Usadas
No java

```shell
jackson 2.17.1 # Disponível no github ou no sourceforge em formato.jar
               # Também disponibilizamos uma cópia junta do código
```

No python 
```shell
# Não foram usadas pacotes externos
```
