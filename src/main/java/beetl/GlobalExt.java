package beetl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

import com.hejinwei.diary.util.Constants;

public class GlobalExt implements WebRenderExt {

	static long version = System.currentTimeMillis();

	@Override
	public void modify(Template template, GroupTemplate arg1, HttpServletRequest arg2, HttpServletResponse arg3) {
		// js,css 的版本编号
		template.binding("sysVersion", version);
		
		template.binding("contextPath", Constants.contextPath);
		template.binding("webName", "XM日记"); // 网站名
		template.binding("webIntroduction", "爱生活，爱留忆，爱吐槽");
		template.binding("icpLicense", "备案还没加");
	}

}
