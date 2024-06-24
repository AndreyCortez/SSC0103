# SSC0103
Programação Orientada a Objeto

## Autores
- Andrey Cortez Rufino - 11819487
- Francyélio de Jesus Campos Lima - 13676537

## Organização e Implementação

A interface de usuário em Java está implementada na pasta source, nela usamos a biblioteca
swing para fazer a classe principal, tratamos cada elemento de UI como se fosse um método
dessa classe, assim poderiamos separar as partes da interface em campos lógicos específicos

Na parte de java também usamos socket juntamente com a 
biblioteca de requests e a biblioteca jackson para fazer a comunicação com o servidor em python

Na pasta database podemos encontrar o código fonte do DBMS desenvolvido na matéria de arquivos,
alteramos ele de acordo com as necessidades do programa, para conversar com eles temos um script em
python chamado de table, que através de chamadas para o programa e uso da stdin consegue fazer uma
wrapper de sua funcionalidade.

Na parte de conectividade usamos um servidor socket com uma camada de abstração para requests HTTP
pois assim seria mais fácil de transferir dados através do protocolo json, o servidor, por padrão
é aberto por padrão na porta 8080.

Em todos os códigos (que não forem os em C) usamos uma abordagem orientada a objeto, na parte de java fizemos POO puro, pois a linguagem requer isso, e na parte de python criamos classes para as funcionalidades principais de cada arquivo mas em certos pontos criamos também funções que não possuem classe para uma melhor modularização e entendimento do código 

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
