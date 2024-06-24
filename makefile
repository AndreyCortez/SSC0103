# Variáveis do compilador e biblioteca
JAVAC = javac
JAVA = java
LIB_DIR = lib
SRC_DIR = src
BIN_DIR = bin
JACKSON_LIBS = $(LIB_DIR)/jackson-core-2.17.1.jar$(PATH_SEPARATOR)$(LIB_DIR)/jackson-databind-2.17.1.jar$(PATH_SEPARATOR)$(LIB_DIR)/jackson-annotations-2.17.1.jar

# Verifica o sistema operacional
ifdef OS
    RM = del /Q
    MKDIR = if not exist $(BIN_DIR) mkdir $(BIN_DIR)
    PATH_SEPARATOR = ;
else
    RM = rm -rf
    MKDIR = mkdir -p $(BIN_DIR)
    PATH_SEPARATOR = :
endif

# Lista de arquivos .java e seus correspondentes .class
SOURCES = $(SRC_DIR)/PlayerSearchApp.java $(SRC_DIR)/HttpClientSocket.java $(SRC_DIR)/Main.java
CLASSES = $(BIN_DIR)/PlayerSearchApp.class $(BIN_DIR)/HttpClientSocket.class $(BIN_DIR)/Main.class

# Regra principal
all: $(CLASSES)

# Compila todos os arquivos .java
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	$(MKDIR)
	$(JAVAC) -d $(BIN_DIR) -cp $(JACKSON_LIBS) $(SOURCES)

# Limpa os arquivos .class
clean:
	$(RM) $(BIN_DIR)/*

# Executa o programa principal
run: all
	$(JAVA) -cp $(BIN_DIR)$(PATH_SEPARATOR)$(JACKSON_LIBS) Main

.PHONY: all clean run
