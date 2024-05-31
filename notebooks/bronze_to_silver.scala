// Databricks notebook source
// MAGIC %md
// MAGIC ##Conferindo se os dados foram montados e se temos acesso a pasta bronze

// COMMAND ----------

// MAGIC %python
// MAGIC dbutils.fs.ls("/mnt/dados/bronze")
// MAGIC

// COMMAND ----------

// MAGIC %md
// MAGIC #Lendo os dados na camada bronze

// COMMAND ----------

val path = "dbfs:/mnt/dados/bronze/dataset_imoveis/"
val df = spark.read.format("delta").load(path)


// COMMAND ----------

display(df)


// COMMAND ----------

// MAGIC %md
// MAGIC #Transformando os campos do json em coluna

// COMMAND ----------

display(df.select("anuncio.*"))

// COMMAND ----------

display(
  df.select("anuncio.*","anuncio.endereco.*")
)

// COMMAND ----------

val dados_detalhados = df.select("anuncio.*","anuncio.endereco.*")

// COMMAND ----------

display(dados_detalhados)

// COMMAND ----------



// COMMAND ----------

// MAGIC %md
// MAGIC #Removendo Colunas

// COMMAND ----------

val df_silver = dados_detalhados.drop("caracteristicas", "endereco")
display(df_silver)

// COMMAND ----------

// MAGIC %md
// MAGIC #Salvando na camada Silver

// COMMAND ----------

val path = "dbfs:/mnt/dados/silver/dataset_imoveis"
df_silver.write.format("delta").mode("overwrite").save(path)


// COMMAND ----------

// MAGIC %md
// MAGIC ###Outros Códigos importantes

// COMMAND ----------

val columnNames = df_silver.columns
columnNames


// COMMAND ----------

val columnNames: Array[String] = df_silver.columns
columnNames


// COMMAND ----------

columnNames.foreach(println)

