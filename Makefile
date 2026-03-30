# Variables
KOTLINC = kotlinc
JAR_NAME = AlfonsoJose.jar
SRCS = AlfonsoJose.kt

# Regla por defecto: compilar el .jar (se ejecuta al escribir solo 'make')
all: $(JAR_NAME)

# Regla para crear el ejecutable .jar
$(JAR_NAME): $(SRCS)
	$(KOTLINC) $(SRCS) -include-runtime -d $(JAR_NAME)
	@echo "Compilación exitosa. Se ha creado $(JAR_NAME)"

# Regla para limpiar los archivos generados
clean:
	rm -f $(JAR_NAME)
	@echo "Limpieza completada. Se ha eliminado $(JAR_NAME)"

# Regla extra: compilar y ejecutar de una vez con el archivo de prueba
run: $(JAR_NAME)
	java -jar $(JAR_NAME) 
