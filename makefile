# Nome do arquivo .java principal
MAIN = Main

# Diretório de origem
SRC_DIR = src

# Diretório de classes compiladas
BIN_DIR = bin

# Diretório de bibliotecas (onde os JARs do Jackson estão localizados)
LIB_DIR = lib

# Compilador Java
JAVAC = javac

# Executor Java
JAVA = java

# Flags de compilação
JFLAGS = -d $(BIN_DIR) -sourcepath $(SRC_DIR) -cp $(LIB_DIR)\jackson-core-2.17.1.jar;$(LIB_DIR)\jackson-databind-2.17.1.jar;$(LIB_DIR)\jackson-annotations-2.17.1.jar

# Flags de execução
RFLAGS = -cp $(BIN_DIR);$(LIB_DIR)\jackson-core-2.17.1.jar;$(LIB_DIR)\jackson-databind-2.17.1.jar;$(LIB_DIR)\jackson-annotations-2.17.1.jar

# Busca por todos os arquivos .java no diretório de origem
SOURCES := $(shell dir /B /S $(SRC_DIR)\*.java)

# Objetivos padrão
.PHONY: all run clean

# Compilar todos os arquivos .java
all: $(BIN_DIR)\$(MAIN).class

# Regra para compilar os arquivos .java
$(BIN_DIR)\$(MAIN).class: $(SOURCES)
	if not exist $(BIN_DIR) mkdir $(BIN_DIR)
	$(JAVAC) $(JFLAGS) $(SOURCES)

# Executar o programa
run: all
	$(JAVA) $(RFLAGS) $(MAIN)

# Limpar arquivos compilados
clean:
	if exist $(BIN_DIR) rmdir /S /Q $(BIN_DIR)

# Exemplo de execução:
# make          -> compila os arquivos .java
# make run      -> executa o programa
# make clean    -> limpa os arquivos compilados
