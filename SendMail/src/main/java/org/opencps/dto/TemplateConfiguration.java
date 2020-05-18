package org.opencps.dto;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * @author trungnt
 */
public class TemplateConfiguration {

	public TemplateConfiguration() {
		init();
	}

	private void init() {
		try {
			Configuration configuration = new Configuration(Configuration.getVersion());
			configuration.setDefaultEncoding("UTF-8");
			configuration.setObjectWrapper(new DefaultObjectWrapper(configuration.getIncompatibleImprovements()));

			this.setConfiguration(configuration);
		} catch (Exception e) {
			//_log.error(e);
		}
	}

	public Configuration getConfiguration() {

		return _configuration;
	}

	public void setConfiguration(Configuration configuration) {

		this._configuration = configuration;
	}

	private Configuration _configuration;
}
