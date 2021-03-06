package vwg.skoda.prcek.objects;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFiltr implements Filter {

	//TODO - pri pristim buildu SMAZAT!
	//Tato trida byla implementovana ve web.xml, ale nahradil jsem ji SPRINGovou variatnou a tim padem jiz tato trida nadale neni potreba!
		
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	// Encoding filtr pro naSETotovani requestu na UTF-8
	// Aktivuje a definuje (mapovani na url) se ve WEB.XML
	// Mozna to na jinem serveru (nez na mem lokalnim TOMCATu) bude normalne fungovat i bez tohoto filtru!
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
