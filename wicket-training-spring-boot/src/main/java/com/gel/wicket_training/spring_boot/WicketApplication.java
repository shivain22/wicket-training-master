package com.gel.wicket_training.spring_boot;

import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;

@SpringBootApplication
public class WicketApplication extends WicketBootStandardWebApplication {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder()
			.sources(WicketApplication.class)
			.run(args);
	}

    @Override
    protected void init() {
        super.init();
        getCspSettings().blocking()
                .add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF)
                .add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.UNSAFE_INLINE)
                .add(CSPDirective.SCRIPT_SRC, CSPDirectiveSrcValue.UNSAFE_INLINE)
                .add(CSPDirective.SCRIPT_SRC, CSPDirectiveSrcValue.SELF)
                .add(CSPDirective.SCRIPT_SRC, CSPDirectiveSrcValue.UNSAFE_EVAL)
                .add(CSPDirective.STYLE_SRC, "https://fonts.googleapis.com/css")
                .add(CSPDirective.FONT_SRC, "https://fonts.gstatic.com");
        getCspSettings().blocking().disabled();
        getResourceSettings().getResourceFinders().
                add(new WebApplicationPath(getServletContext(),"/"));
        getComponentInstantiationListeners().add(new SpringComponentInjector(this,
                WebApplicationContextUtils.getRequiredWebApplicationContext(
                        getServletContext())));
    }
}
