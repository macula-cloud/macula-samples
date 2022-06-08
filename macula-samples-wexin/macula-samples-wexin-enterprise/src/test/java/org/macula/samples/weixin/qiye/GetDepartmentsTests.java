package org.macula.samples.weixin.qiye;

import java.io.IOException;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.junit.jupiter.api.Test;
import org.macula.samples.weixin.enterprise.WeixinEnterpriseApplication;
import org.macula.samples.weixin.enterprise.config.WxCpConfiguration;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WeixinEnterpriseApplication.class)
public class GetDepartmentsTests {

	// @Test
	public void getOrganizationsTest() throws WxErrorException {
		int agentId = 1000038;
		final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
		WxCpDepartmentService departService = wxCpService.getDepartmentService();
		WxCpUserService userService = wxCpService.getUserService();
		List<WxCpDepart> depts = departService.list(null);
		for (WxCpDepart wxCpDepart : depts) {
			System.out.println(wxCpDepart);
			List<WxCpUser> deptUsers = userService.listByDepartment(wxCpDepart.getId(), false, 1);
			deptUsers.stream().forEach((u) -> {
				System.out.println("\t" + u);
			});
		}

		depts.stream().forEach(System.out::println);
	}

	@Test
	public void getLoginQRTest() throws IOException {
		int agentId = 1000038;
		String loginCallback = "http://53h7s50062.qicp.vip/sso/cp/" + agentId;
		String state = IdUtil.getSnowflakeNextIdStr();
		final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
		String url = wxCpService.buildQrConnectUrl(loginCallback, state);
		System.out.println(url);
	}
}
