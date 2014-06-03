package com.tihonchik.lenonhonor360.parser;

import java.util.regex.Pattern;

public interface HtmlPatterns {
	public Pattern hrefPattern = Pattern
			.compile("(?i)<a_style([^>]+)>(.+?)</a>");
	public Pattern idPattern = Pattern.compile("(?i)id=(\\d{1,})");
	public Pattern datePattern = Pattern
			.compile("(?i)<div_style=(\\\"color:#66[^>]+)>(.+?)</div>");
	public Pattern textWithNoImagePattern = Pattern
			.compile("(?i)<div_style=(\\\"color:#33[^>]+)>(.+?)</div>");
	public Pattern textWithImagePattern = Pattern
			.compile("(?i)<div_style=(\\\"color:#33[^>]+)>(.+?)(</p>_</div>|div>(.+?)_</div>)");
	public Pattern paragraphPattern = Pattern.compile("(?i)<p>(.+?)</p>");
	public Pattern imageSourcePattern = Pattern
			.compile("(?i)<img_src=(\\\"([^\"]*)\\\"|'[^']*'|([^'\">\\s]+))");
}