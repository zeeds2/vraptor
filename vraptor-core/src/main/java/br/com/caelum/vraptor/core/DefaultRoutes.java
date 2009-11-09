package br.com.caelum.vraptor.core;

import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;

/**
 * Default implementation of route info extractor.
 * @author guilherme silveira
 * @since 3.0.3
 */
@Component
public class DefaultRoutes implements Routes{
	
	private final Proxifier proxifier;
	private final Router router;

	public DefaultRoutes(Router router, Proxifier proxifier) {
		this.router = router;
		this.proxifier = proxifier;
	}
	
	private String uri;

	public <T> T uriFor(final Class<T> type) {
		return proxifier.proxify(type, new MethodInvocation<T>() {
			public Object intercept(T proxy, java.lang.reflect.Method method,
					Object[] args, br.com.caelum.vraptor.proxy.SuperMethod superMethod) {
				uri = router.urlFor(type, method, args);
				return null;
			};
		});
	}

	public String getUri() {
		return uri;
	}

}
