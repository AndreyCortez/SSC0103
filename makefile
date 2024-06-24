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

# Verificar o sistema operacional
OS := $(shell uname -s)

# Configurações específicas para Windows
ifeq ($(OS),Windows_NT)
    PATH_SEP = ;
    MKDIR = if not exist $(BIN_DIR) mkdir $(BIN_DIR)
    RM = if exist $(BIN_DIR) rmdir /S /Q $(BIN_DIR)
    FIND_SRCS = $(shell dir /B /S $(SRC_DIR)\*.java)
else
    PATH_SEP = :
    MKDIR = mkdir -p $(BIN_DIR)
    RM = rm -rf $(BIN_DIR)
    FIND_SRCS = $(shell find $(SRC_DIR) -name '*.java')
endif

# Flags de compilação
JFLAGS = -d $(BIN_DIR) -sourcepath $(SRC_DIR) -cp $(LIB_DIR)/jackson-core-2.17.1.jar$(PATH_SEP)$(LIB_DIR)/jackson-databind-2.17.1.jar$(PATH_SEP)$(LIB_DIR)/jackson-annotations-2.17.1.jar

# Flags de execução
RFLAGS = -cp $(BIN_DIR)$(PATH_SEP)$(LIB_DIR)/jackson-core-2.17.1.jar$(PATH_SEP)$(LIB_DIR)/jackson-databind-2.17.1.jar$(PATH_SEP)$(LIB_DIR)/jackson-annotations-2.17.1.jar

# Busca por todos os arquivos .java no diretório de origem
SOURCES := $(FIND_SRCS)

# Objetivos padrão
.PHONY: all run clean

# Compilar todos os arquivos .java
all: $(BIN_DIR)/$(MAIN).class

# Regra para compilar os arquivos .java
$(BIN_DIR)/$(MAIN).class: $(SOURCES)
	$(MKDIR)
	$(JAVAC) $(JFLAGS) $(SOURCES)

# Executar o programa
run: all
	$(JAVA) $(RFLAGS) $(MAIN)

# Limpar arquivos compilados
clean:
	$(RM)
