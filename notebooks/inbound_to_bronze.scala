// Databricks notebook source
// MAGIC %md
// MAGIC ##Conferindo se os dados foram montados e se temos acesso a pasta inbound
// MAGIC

// COMMAND ----------

// MAGIC %python
// MAGIC dbutils.fs.ls("/mnt/dados/inbound")

// COMMAND ----------

// MAGIC %md
// MAGIC #Lendo os dados na camada inbound

// COMMAND ----------

val path = "dbfs:/mnt/dados/inbound/dados_brutos_imoveis.json"
val dados = spark.read.json(path)

// COMMAND ----------

display(dados)

// COMMAND ----------

// MAGIC %md
// MAGIC #Removendo Colunas

// COMMAND ----------

val dados_anuncio = dados.drop("imagens","usuario")
display(dados_anuncio)

// COMMAND ----------

// MAGIC %md
// MAGIC #Criando coluna de identificação

// COMMAND ----------

import org.apache.spark.sql.functions.col

// COMMAND ----------

val df_bronze = dados_anuncio.withColumn("id", col("anuncio.id"))
display(df_bronze)

// COMMAND ----------

// MAGIC %md
// MAGIC #Salvando na camada bronze

// COMMAND ----------

val path = "dbfs:/mnt/dados/bronze/dataset_imoveis"
df_bronze.write.format("delta").mode(SaveMode.Overwrite).save(path)

// COMMAND ----------

// MAGIC %md
// MAGIC ##Salvando uma coluna como um dataset na camada bronze

// COMMAND ----------

val df_endereco = df_bronze.select("anuncio.endereco")
display(df_endereco)


// COMMAND ----------

val path = "dbfs:/mnt/dados/bronze/dataset_endereco"
df_endereco.write.format("parquet").save(path)

// COMMAND ----------


