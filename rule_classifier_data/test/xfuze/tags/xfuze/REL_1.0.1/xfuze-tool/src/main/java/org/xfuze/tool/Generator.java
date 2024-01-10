/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xfuze.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xfuze.tool.lang.Constant;
import org.xfuze.tool.model.Dao;
import org.xfuze.tool.model.DaoImpl;
import org.xfuze.tool.model.Model;
import org.xfuze.tool.model.ModelField;
import org.xfuze.tool.model.Schema;
import org.xfuze.tool.model.SchemaField;
import org.xfuze.tool.model.Service;
import org.xfuze.tool.model.ServiceImpl;
import org.xfuze.tool.model.ServiceTest;
import org.xfuze.tool.model.Spring;
import org.xfuze.tool.model.SqlMap;
import org.xfuze.tool.model.SqlMapField;
import org.xfuze.tool.model.SqlMapTypeAlias;
import org.xfuze.tool.util.DatabaseUtils;
import org.xfuze.tool.util.JavaUtils;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Jason Chan
 *
 */
public class Generator {
	private static final transient Logger logger = LoggerFactory.getLogger(Generator.class);

	private static final String OUTPUT_CONSOLE = "CONSOLE";
	private static final String OUTPUT_FILE = "FILE";

	private static Configuration configuration;
	private Connection connection;
	private DatabaseMetaData databaseMetaData;

	private Map<String, Object> dataModel = new HashMap<String, Object>();

	static {
		ConfigurationFactory factory = new ConfigurationFactory(Constant.CONFIGURATION_FILE);
		try {
			configuration = factory.getConfiguration();
		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
			System.exit(-1);
		}
	}

	public Generator() throws ClassNotFoundException, SQLException {
		String driver = configuration.getString(configuration.getString(Constant.DATABASE_DRIVER));
		String url = configuration.getString(configuration.getString(Constant.DATABASE_URL));
		String username = configuration.getString(configuration.getString(Constant.DATABASE_USERNAME));
		String password = configuration.getString(configuration.getString(Constant.DATABASE_PASSWORD));

		Class.forName(driver);
		connection = DriverManager.getConnection(url, username, password);
		databaseMetaData = connection.getMetaData();

		logger.info("==================================================");
		logger.info("Database        : {}", databaseMetaData.getDatabaseProductName());
		logger.info("Database Version: {}", databaseMetaData.getDatabaseProductVersion());
		logger.info("Driver          : {}", databaseMetaData.getDriverName());
		logger.info("Driver Version  : {}", databaseMetaData.getDriverVersion());
		logger.info("==================================================");
	}

	public void generate() throws IOException, SQLException, TemplateException {
		String tableNamePattern = configuration.getString(Constant.TABLE_NAME_PATTERN);

		ResultSet rsTable = databaseMetaData.getTables(null, null, tableNamePattern, null);
		while (rsTable.next()) {
			String tableName = rsTable.getString("TABLE_NAME");
			logger.info("**************************************************");
			logger.info("Table: {}", tableName);
			tableName = tableName.toLowerCase();
			logger.info("Table: {}", tableName);
			String tableNameSuffix = configuration.getString(Constant.TABLE_NAME_SUFFIX);

			// java model
			String modelPath = configuration.getString(Constant.MODEL_PATH);
			String modelSuffix = configuration.getString(Constant.MODEL_SUFFIX);
			String modelPackage = configuration.getString(Constant.MODEL_PACKAGE);
			String modelName = JavaUtils.getModelName(tableName, tableNameSuffix, modelSuffix);
			Model model = new Model();
			model.setJavaPackage(modelPackage);
			model.setJavaName(modelName);
			model.setPath(modelPath);

			// java model (audit)
			String modelAuditPath = configuration.getString("path.model.audit");
			String modelAuditSuffix = configuration.getString("model.audit.suffix");
			String modelAuditPackage = configuration.getString("model.audit.package");
			String modelAuditName = JavaUtils.getModelName(tableName, tableNameSuffix, modelAuditSuffix);
			Model modelAudit = new Model();
			modelAudit.setJavaPackage(modelAuditPackage);
			modelAudit.setJavaName(modelAuditName);
			modelAudit.setPath(modelAuditPath);

			// java dao interface
			String daoPath = configuration.getString(Constant.DAO_PATH);
			String daoSuffix = configuration.getString(Constant.DAO_SUFFIX);
			String daoPackage = configuration.getString(Constant.DAO_IMPL_PACKAGE);
			String daoName = JavaUtils.getDaoName(tableName, tableNameSuffix, daoSuffix);
			Dao dao = new Dao();
			dao.setJavaPackage(daoPackage);
			dao.setJavaName(daoName);
			dao.setPath(daoPath);
			dao.getJavaImports().add(model.getJavaPackage() + "." + model.getJavaName());
			dao.setModel(model);

			// java dao impl
			String daoImplPath = configuration.getString(Constant.DAO_IMPL_PATH);
			String daoImplSuffix = configuration.getString(Constant.DAO_IMPL_SUFFIX);
			String daoImplPackage = configuration.getString(Constant.DAO_IMPL_PACKAGE);
			String daoImplName = JavaUtils.getDaoName(tableName, tableNameSuffix, daoImplSuffix);
			DaoImpl daoImpl = new DaoImpl();
			daoImpl.setJavaPackage(daoImplPackage);
			daoImpl.setJavaName(daoImplName);
			daoImpl.setPath(daoImplPath);
			daoImpl.getJavaImports().add(model.getJavaPackage() + "." + model.getJavaName());
			daoImpl.getJavaImports().add(dao.getJavaPackage() + "." + dao.getJavaName());
			daoImpl.setModel(model);
			daoImpl.setDao(dao);

			// java service interface
			String servicePath = configuration.getString(Constant.SERVICE_PATH);
			String serviceSuffix = configuration.getString(Constant.SERVICE_SUFFIX);
			String servicePackage = configuration.getString(Constant.SERVICE_PACKAGE);
			String serviceName = JavaUtils.getDaoName(tableName, tableNameSuffix, serviceSuffix);
			Service service = new Service();
			service.setJavaPackage(servicePackage);
			service.setJavaName(serviceName);
			service.setPath(servicePath);
			service.getJavaImports().add(model.getJavaPackage() + "." + model.getJavaName());
			service.setModel(model);

			// java service impl
			String serviceImplPath = configuration.getString(Constant.SERVICE_IMPL_PATH);
			String serviceImplSuffix = configuration.getString(Constant.SERVICE_IMPL_SUFFIX);
			String serviceImplPackage = configuration.getString(Constant.SERVICE_IMPL_PACKAGE);
			String serviceImplName = JavaUtils.getDaoName(tableName, tableNameSuffix, serviceImplSuffix);
			ServiceImpl serviceImpl = new ServiceImpl();
			serviceImpl.setJavaPackage(serviceImplPackage);
			serviceImpl.setJavaName(serviceImplName);
			serviceImpl.setPath(serviceImplPath);
			serviceImpl.getJavaImports().add(model.getJavaPackage() + "." + model.getJavaName());
			serviceImpl.getJavaImports().add(dao.getJavaPackage() + "." + dao.getJavaName());
			serviceImpl.getJavaImports().add(service.getJavaPackage() + "." + service.getJavaName());
			serviceImpl.setModel(model);
			serviceImpl.setDao(dao);
			serviceImpl.setService(service);

			// java service test
			String serviceTestPath = configuration.getString(Constant.SERVICE_TEST_PATH);
			String serviceTestSuffix = configuration.getString(Constant.SERVICE_TEST_SUFFIX);
			String serviceTestPackage = configuration.getString(Constant.SERVICE_TEST_PACKAGE);
			boolean primaryKeyGenerated = configuration.getBoolean(Constant.PRIMARY_KEY_GENERATED);
			String serviceTestName = JavaUtils.getDaoName(tableName, tableNameSuffix, serviceTestSuffix);
			ServiceTest serviceTest = new ServiceTest();
			serviceTest.setJavaPackage(serviceTestPackage);
			serviceTest.setJavaName(serviceTestName);
			serviceTest.setPrimaryKeyGenerated(primaryKeyGenerated);
			serviceTest.setPath(serviceTestPath);
			serviceTest.setModel(model);
			// serviceTest.setDao(dao);
			serviceTest.setService(service);
			serviceTest.getJavaImports().add(model.getJavaPackage() + "." + model.getJavaName());
			// serviceTest.getJavaImports().add(dao.getJavaPackage() + "." + dao.getJavaName());
			serviceTest.getJavaImports().add(service.getJavaPackage() + "." + service.getJavaName());
			serviceTest.getJavaImports().addAll(model.getJavaImports());

			// spring xml
			String springPath = configuration.getString(Constant.SPRING_PATH);
			String springName = JavaUtils.getSpringName(tableName, tableNameSuffix);
			Spring spring = new Spring();
			spring.setPath(springPath);
			spring.setSpringName(springName);
			spring.setModel(model);
			spring.setDao(dao);
			spring.setDaoImpl(daoImpl);
			// spring.setAuditDao(daoAudit);
			spring.setService(service);
			spring.setServiceImpl(serviceImpl);

			// sqlmap xml
			String database = configuration.getString(Constant.SQLMAP_DATABASE);
			String sqlmapPath = configuration.getString(Constant.SQLMAP_PATH);
			String updateControl = configuration.getString(Constant.SQLMAP_CONTROL_UPDATE);
			String deleteControl = configuration.getString(Constant.SQLMAP_CONTROL_DELETE);
			SqlMap sqlmap = new SqlMap();
			sqlmap.setDatabase(database);
			sqlmap.setPath(sqlmapPath);
			sqlmap.setTableName(tableName);
			sqlmap.setModel(model);
			sqlmap.setUpdateControl(updateControl);
			sqlmap.setDeleteControl(deleteControl);
			// sqlmap - alias
			String handlerLikeName = configuration.getString(Constant.SQLMAP_HANDLER_LIKE_NAME);
			String handlerLikeClass = configuration.getString(Constant.SQLMAP_HANDLER_LIKE_CLASS);
			sqlmap.setLikeHandlerAlias(handlerLikeName);
			sqlmap.getTypeAliases().add(new SqlMapTypeAlias(handlerLikeName, handlerLikeClass));
			sqlmap.getTypeAliases().add(
					new SqlMapTypeAlias(model.getInstanceName(), model.getJavaPackage() + "." + model.getJavaName()));

			// sqlmap xml (audit)
			String sqlmapAuditPath = configuration.getString("path.sqlmap.audit");
			SqlMap sqlmapAudit = new SqlMap();
			sqlmapAudit.setPath(sqlmapAuditPath);
			sqlmapAudit.setTableName(tableName);
			sqlmapAudit.setModel(modelAudit);
			// sqlmap - alias (audit)
			sqlmapAudit.getTypeAliases().add(
					new SqlMapTypeAlias(modelAudit.getInstanceName(), modelAudit.getJavaPackage() + "."
							+ modelAudit.getJavaName()));

			// schema & (for trigger)
			String schemaPath = configuration.getString(Constant.SCHEMA_PATH);
			Schema schema = new Schema();
			schema.setPath(schemaPath);
			schema.setModelName(modelName);
			schema.setTableName(tableName);

			// database column
			ResultSet rsColumn = databaseMetaData.getColumns(null, null, tableName, null);
			while (rsColumn.next()) {
				// java model
				String javaFieldType = JavaUtils.getJavaFieldType(rsColumn.getInt("DATA_TYPE"));
				String javaFieldName = JavaUtils.getJavaFieldName(rsColumn.getString("COLUMN_NAME"));
				int columnSize = rsColumn.getInt("COLUMN_SIZE");
				int decimalDigit = rsColumn.getInt("DECIMAL_DIGITS");

				if (javaFieldType.indexOf(".") != -1) {
					model.getJavaImports().add(javaFieldType);
					modelAudit.getJavaImports().add(javaFieldType);
					javaFieldType = javaFieldType.substring(javaFieldType.lastIndexOf(".") + 1);
				}

				ModelField modelField = new ModelField();
				modelField.setFieldType(javaFieldType);
				modelField.setFieldName(javaFieldName);
				modelField.setColumnSize(columnSize);
				modelField.setDecimalDigit(decimalDigit);
				model.getModelFields().add(modelField);
				modelAudit.getModelFields().add(modelField);

				// database field
				SqlMapField sqlMapField = new SqlMapField();
				sqlMapField.setJdbcType(DatabaseUtils.getJdbcType(rsColumn.getInt("DATA_TYPE")));
				sqlMapField.setJavaFieldName(JavaUtils.getJavaFieldName(rsColumn.getString("COLUMN_NAME")));
				sqlMapField.setDatabaseFieldType(DatabaseUtils.getDatabaseFieldType(rsColumn.getInt("DATA_TYPE"),
						rsColumn.getString("TYPE_NAME"), rsColumn.getInt("COLUMN_SIZE"), rsColumn
								.getInt("DECIMAL_DIGITS")));
				sqlMapField.setDatabaseFieldName(rsColumn.getString("COLUMN_NAME"));

				sqlMapField.setExactMatch(DatabaseUtils.exactMatch(rsColumn.getInt("DATA_TYPE")));
				sqlMapField.setLikeMatch(DatabaseUtils.likeMatch(rsColumn.getInt("DATA_TYPE")));

				sqlmap.getSqlMapFields().add(sqlMapField);
				sqlmap.getUpdateFields().add(sqlMapField);

				sqlmapAudit.getSqlMapFields().add(sqlMapField);
				sqlmapAudit.getUpdateFields().add(sqlMapField);

				// schema field
				SchemaField field = new SchemaField();
				field.setDatabaseFieldType(DatabaseUtils.getDatabaseFieldType(rsColumn.getInt("DATA_TYPE"), rsColumn
						.getString("TYPE_NAME"), rsColumn.getInt("COLUMN_SIZE"), rsColumn.getInt("DECIMAL_DIGITS")));
				field.setDatabaseFieldName(rsColumn.getString("COLUMN_NAME"));
				field.setNullable(DatabaseUtils.getNullable(rsColumn.getInt("NULLABLE")));

				schema.getSchemaFields().add(field);
			}

			// primary key
			List<String> javaPrimaryKeyFieldNames = new ArrayList<String>();
			ResultSet rsPrimaryKey = databaseMetaData.getPrimaryKeys(null, null, tableName);
			while (rsPrimaryKey.next()) {
				// java
				String javaFieldName = JavaUtils.getJavaFieldName(rsPrimaryKey.getString("COLUMN_NAME"));
				javaPrimaryKeyFieldNames.add(javaFieldName);

				// database
				SqlMapField field = new SqlMapField();
				field.setJavaFieldName(JavaUtils.getJavaFieldName(rsPrimaryKey.getString("COLUMN_NAME")));
				field.setDatabaseFieldName(rsPrimaryKey.getString("COLUMN_NAME"));

				int index = sqlmap.getUpdateFields().indexOf(field);

				if (index != -1) {
					sqlmap.getPrimaryKeyFields().add(sqlmap.getUpdateFields().get(index));
					sqlmap.getUpdateFields().remove(index);
				}

				index = sqlmapAudit.getUpdateFields().indexOf(field);
				if (index != -1) {
					sqlmapAudit.getPrimaryKeyFields().add(sqlmapAudit.getUpdateFields().get(index));
					sqlmapAudit.getUpdateFields().remove(index);
				}

				// schema
				SchemaField schemaField = new SchemaField();
				schemaField.setDatabaseFieldName(rsPrimaryKey.getString("COLUMN_NAME"));

				schema.getPrimaryKeyFields().add(schemaField);
			}

			// unique constraint
			ResultSet rsIndexInfo = databaseMetaData.getIndexInfo(null, null, tableName, true, false);
			while (rsIndexInfo.next()) {
				String databaseFieldName = rsIndexInfo.getString("COLUMN_NAME");
				if (databaseFieldName != null) {
					SchemaField field = new SchemaField();
					field.setDatabaseFieldName(databaseFieldName);

					if (schema.getPrimaryKeyFields().indexOf(field) == -1) { // not primary key
						logger.info("Index[" + rsIndexInfo.getString("INDEX_NAME") + "]: " + databaseFieldName
								+ " --> " + rsIndexInfo.getBoolean("NON_UNIQUE"));

						schema.getUniqueFields().add(field);
					}
				}
			}

			// foreign key constraint
			ResultSet rsImportedKey = databaseMetaData.getImportedKeys(null, null, tableName);
			while (rsImportedKey.next()) {
				logger.info("FK: " + rsImportedKey.getString("FKCOLUMN_NAME") + " --> "
						+ rsImportedKey.getString("PKTABLE_NAME") + "(" + rsImportedKey.getString("PKCOLUMN_NAME")
						+ ")");

				SchemaField field = new SchemaField();
				field.setDatabaseFieldName(rsImportedKey.getString("FKCOLUMN_NAME"));
				field.setForeignKeyTableName(rsImportedKey.getString("PKTABLE_NAME"));
				field.setForeignKeyFieldName(rsImportedKey.getString("PKCOLUMN_NAME"));

				schema.getForeignKeyFields().add(field);
			}

			for (ModelField modelField : model.getModelFields()) {
				int index = javaPrimaryKeyFieldNames.indexOf(modelField.getFieldName());
				if (index != -1) {
					// primaryKey.getPrimaryKeyFields().add(modelField);
					model.getPrimaryKeyFields().add(modelField);
					modelAudit.getPrimaryKeyFields().add(modelField);
					dao.getPrimaryKeyFields().add(modelField);
					daoImpl.getPrimaryKeyFields().add(modelField);
					service.getPrimaryKeyFields().add(modelField);
					serviceImpl.getPrimaryKeyFields().add(modelField);
					serviceTest.getPrimaryKeyFields().add(modelField);
				} else {
					serviceTest.getNonPrimaryKeyFields().add(modelField);
				}
			}

			Map<String, Object> levelJava = new HashMap<String, Object>();
			levelJava.put("model", model); // java.model
			levelJava.put("dao", dao); // java.dao
			levelJava.put("daoImpl", daoImpl); // java.daoImpl
			levelJava.put("service", service); // java.service
			levelJava.put("serviceImpl", serviceImpl); // java.serviceImpl
			levelJava.put("serviceTest", serviceTest); // java.serviceTest

			Map<String, Object> levelAudit = new HashMap<String, Object>();
			levelAudit.put("model", modelAudit);
			levelAudit.put("sqlmap", sqlmapAudit);

			dataModel.put("java", levelJava);
			dataModel.put("spring", spring);
			dataModel.put("sqlmap", sqlmap);
			dataModel.put("schema", schema);
			dataModel.put("audit", levelAudit);

			// file related
			String copyright = configuration.getString("file.copyright");
			String author = configuration.getString("file.author");
			dataModel.put("copyright", copyright);
			dataModel.put("author", author);

			// create and adjust the configuration
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			cfg.setDirectoryForTemplateLoading(new File(configuration.getString("path.template")));
			cfg.setObjectWrapper(new DefaultObjectWrapper());

			Template templateModel = cfg.getTemplate(configuration.getString("template.model"));
			Template templateModelAudit = cfg.getTemplate(configuration.getString("template.model.audit"));
			// Template templateDao = cfg.getTemplate(configuration.getString("template.dao"));
			// Template templateDaoImpl = cfg.getTemplate(configuration.getString("template.dao.impl"));
			// Template templateService = cfg.getTemplate(configuration.getString("template.service"));
			// Template templateServiceImpl = cfg.getTemplate(configuration.getString("template.service.impl"));
			// Template templateServiceTest = cfg.getTemplate(configuration.getString("template.service.test"));
			// Template templateSpring = cfg.getTemplate(configuration.getString("template.spring"));
			// Template templateSqlMap = cfg.getTemplate(configuration.getString("template.sqlmap"));
			// Template templateSqlMapCustom = cfg.getTemplate(configuration.getString("template.sqlmap.custom"));
			// Template templateSchema = cfg.getTemplate(configuration.getString("template.schema"));
			Template templateSchemaLog = cfg.getTemplate(configuration.getString("template.schema.log"));
			// Template templateTrigger = cfg.getTemplate(configuration.getString("template.trigger"));

			output(templateModel, model.getPath(), model.getFileName());
			output(templateModelAudit, modelAudit.getPath(), modelAudit.getFileName());
			// output(templateDao, dao.getPath(), dao.getFileName());
			// output(templateDaoImpl, daoImpl.getPath(), daoImpl.getFileName());
			// output(templateService, service.getPath(), service.getFileName());
			// output(templateServiceImpl, serviceImpl.getPath(), serviceImpl.getFileName());
			// output(templateServiceTest, serviceTest.getPath(), serviceTest.getFileName());
			// output(templateSpring, spring.getPath(), spring.getFileName());
			// output(templateSqlMap, sqlmap.getPath(), sqlmap.getFileName());
			// output(templateSqlMapCustom, configuration.getString("path.sqlmap.custom"), sqlmap.getFileName());
			// output(templateSchema, schema.getPath(), schema.getFileName());
			output(templateSchemaLog, configuration.getString("path.schema.log"), schema.getFileName());
			// output(templateTrigger, configuration.getString("path.trigger"), schema.getFileName());

			String path = configuration.getString("template.sqlmap.path");
			List<String> files = configuration.getList("template.sqlmap.file");
			for (String file : files) {
				logger.debug(">>> " + file);
				Template template = cfg.getTemplate(path + file);
				output(template, sqlmap.getPath(), sqlmap.getFileName(file));
			}

			logger.info("**************************************************");
		}
	}

	private void output(Template template, String path, String fileName) throws IOException, TemplateException {
		String outputType = configuration.getString("output.type");
		if (OUTPUT_CONSOLE.equals(outputType)) {
			Writer out = new OutputStreamWriter(System.out);
			template.process(dataModel, out);
			out.flush();
		} else if (OUTPUT_FILE.equals(outputType)) {
			File pathOutput = new File(path);
			if (!pathOutput.exists()) {
				pathOutput.mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(path + fileName);
			Writer out = new OutputStreamWriter(fos);
			template.process(dataModel, out);
			out.flush();
			out.close();
		}
	}

	public static void main(String[] args) throws Exception {
		Generator generator = new Generator();
		generator.generate();
	}
}
