# Databricks notebook source
# MAGIC %sh
# MAGIC ls /databricks/init_scripts/

# COMMAND ----------

# MAGIC %python
# MAGIC script = """
# MAGIC sed -i "s/^exit 101$/exit 0/" /usr/sbin/policy-rc.d
# MAGIC Wget
# MAGIC https://raw.githubusercontent.com/Microsoft/OMS-Agent-for-Linux/master/i
# MAGIC nstaller/scripts/onboard agent.sh & & sh onboard agent. sh -w
# MAGIC "413ccdad-55b1-49fb-b3d1-21759dc9414b" -S "UZ7Sdshkh+jdYSppCqpAQc48s35Dyso8Sqf+8kqKhdtPsko7Fde9W+oRAJsKxtyI5pxrfijBryONbmRbrR4m5A=="
# MAGIC sudo su omsagent -c 'python
# MAGIC /opt/microsoft/omsconfig/Scripts/PerformRequiredConfigurationChecks .py'
# MAGIC /opt/microsoft/omsagent/bin/service control restart
# MAGIC "413ccdad-55b1-49fb-b3d1-21759dc9414b"
# MAGIC """
# MAGIC dbutils.fs.put("/databricks/my init scripts/configure-omsagent.sh",
# MAGIC script, True)

# COMMAND ----------

# MAGIC %sh
# MAGIC ls "/databricks/init_scripts/"