import subprocess
import json
import os
import sys

# Define o comando que será executado
comando = ''

plataforma = sys.platform

# Verifica o identificador da plataforma
if plataforma.startswith("win"):
    comando = ["./programaTrab.exe"]
elif plataforma.startswith("linux"):
    comando = ["./programaTrab"]


def listar_arquivos(caminho):
    with os.scandir(caminho) as entradas:
        return [entrada.name for entrada in entradas if entrada.is_file()]

def tratar_resultado(resultado):
    ret = []

    if resultado.startswith("Falha no processamento do arquivo."):
        return json.dumps(ret)

    resultado = resultado.split('\n')
    if resultado[-1] == '':
        resultado.pop()

    for i in range(int(len(resultado)/5)):
        ret.append(
            [
                resultado[5*i + 0],
                resultado[5*i + 1],
                resultado[5*i + 2],
                resultado[5*i + 3],
                resultado[5*i + 4]
            ]
        )
    
    return json.dumps(ret)


class Tabela:
    def __init__(self):
        self.tabela_caminho = '' 
        self.csv_caminho  = ''

    def disponiveis(self):
        return json.dumps(listar_arquivos("arquivos"))
    
    def carregar(self, tabela_caminho):
        self.tabela_caminho = 'arquivos/' + tabela_caminho.replace('"', '')
        print(self.tabela_caminho)

    def criar(self, csv_caminho, tabela_caminho):
        if os.path.exists(tabela_caminho):
            os.remove(tabela_caminho)

        self.tabela_caminho = tabela_caminho
        self.csv_caminho = csv_caminho

        entrada_padrao = "1 " + csv_caminho + " " + tabela_caminho
        resultado = subprocess.run(comando, input=entrada_padrao, capture_output=True, text=True)

    def todos_itens(self):
        entrada_padrao = "2 " + self.tabela_caminho

        resultado = subprocess.run(comando, input=entrada_padrao, capture_output=True, text=True)
        return tratar_resultado(resultado.stdout)


    def procurar_item(self, colunas, valores):
        entrada_padrao = "3 " + self.tabela_caminho + " 1 " + str(len(colunas))

        for i in range(len(colunas)):
            if colunas[i] == "nomeJogador" or colunas[i] == "clubeJogador" or colunas[i] == "nacionalidade": 
                entrada_padrao += " " + colunas[i] + " \"" + str(valores[i]) + "\""
            else:
                entrada_padrao += " " + colunas[i] + " " + str(valores[i]) 

        resultado = subprocess.run(comando, input=entrada_padrao, capture_output=True, text=True)
        return tratar_resultado(resultado.stdout)

    def deletar_item(self, colunas, valores):
        entrada_padrao = "5 " + self.tabela_caminho + " index.bin" + " 1 " + str(len(colunas))

        for i in range(len(colunas)):
            if colunas[i] == "nomeJogador" or colunas[i] == "clubeJogador" or colunas[i] == "nacionalidade": 
                entrada_padrao += " " + colunas[i] + " \"" + str(valores[i]) + "\""
            else:
                entrada_padrao += " " + colunas[i] + " " + str(valores[i]) 

        resultado = subprocess.run(comando, input=entrada_padrao, capture_output=True, text=True)
        return tratar_resultado(resultado.stdout)

    def inserir_item(self, valores):
        entrada_padrao = "6 " + self.tabela_caminho + " index.bin" + " 1 " 

        entrada_padrao += " " + str(valores[0])
        entrada_padrao += " " + str(valores[1])
        entrada_padrao += " \"" + str(valores[2]) + "\""
        entrada_padrao += " \"" + str(valores[3]) + "\""
        entrada_padrao += " \"" + str(valores[4]) + "\""

        resultado = subprocess.run(comando, input=entrada_padrao, capture_output=True, text=True)
        return json.dumps([])
    
