package org.opencps.dto;



import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;

/**
 * @author trungnt
 */
public class TemplateProcessor {

	private Template _template;

	public TemplateProcessor(String ftlTemplate) {
		Template template = initTemplate(ftlTemplate);
		this.setTemplate(template);
	}

	public String process(Map<String, Object> dataModel) throws Exception {

		StringWriter out = null;

		String result = "";

		if (getTemplate() != null && dataModel != null) {
			try {
				out = new StringWriter();

				getTemplate().process(dataModel, out);
				result = out.getBuffer().toString();
			}
			catch (Exception e) {
				//_log.error(e);
			}
			finally {
				if (out != null) {
					out.flush();
					try {
						out.close();
					}
					catch (IOException e) {
						//_log.debug(e);
					}
				}
			}
		}
		else {
			throw new Exception();
		}

		return result;
	}

	public String process(Object dataModel) throws Exception {

		StringWriter out = null;

		String result = "";

		if (getTemplate() != null && dataModel != null) {
			try {
				out = new StringWriter();
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("model", dataModel);
				getTemplate().process(root, out);
				result = out.getBuffer().toString();
			}
			catch (Exception e) {
				//_log.error(e);
			}
			finally {
				if (out != null) {
					out.flush();
					try {
						out.close();
					}
					catch (IOException e) {
						//_log.debug(e);
					}
				}
			}
		}
		else {
			throw new Exception();
		}

		return result;
	}

	public String process(MessageDataModel dataModel) throws Exception {

		StringWriter out = null;

		String result = "";

		if (getTemplate() != null && dataModel != null) {
			try {
				out = new StringWriter();
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("model", dataModel);
				getTemplate().process(root, out);
				result = out.getBuffer().toString();
			}
			catch (Exception e) {
				//_log.error(e);
			}
			finally {
				if (out != null) {
					out.flush();
					try {
						out.close();
					}
					catch (IOException e) {
						//_log.debug(e);
						//_log.error(e);
					}
				}
			}
		}
		else {
			throw new Exception();
		}

		return result;
	}

	public Template initTemplate(String ftlTemplate) {

		Template template = null;
		if (ftlTemplate != null) {
			try {

				TemplateConfiguration templateConfiguration =
					new TemplateConfiguration();        

				template = new Template(
						"tmp_" +
						System.currentTimeMillis(),
					ftlTemplate, templateConfiguration.getConfiguration());
			}
			catch (Exception e) {
				//_log.error(e);
			}

		}

		return template;
	}

	public Template getTemplate() {

		return _template;
	}

	public void setTemplate(Template template) {

		this._template = template;
	}

}
