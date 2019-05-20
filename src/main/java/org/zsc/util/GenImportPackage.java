package org.zsc.util;

public class GenImportPackage {
	public static String iInputStream = "import java.io.InputStream;\r\n";
	public static String iResource = "import javax.annotation.Resource;\r\n";
	public static String iAutowired = "import org.springframework.beans.factory.annotation.Autowired;\r\n";

	public static String iAction = "import org.apache.struts2.convention.annotation.Action;\r\n";
	public static String iResult = "import org.apache.struts2.convention.annotation.Result;\r\n";
	public static String iResults = "import org.apache.struts2.convention.annotation.Results;\r\n";
	public static String iNamespace = "import org.apache.struts2.convention.annotation.Namespace;\r\n";
	public static String iModelDriven = "import com.opensymphony.xwork2.ModelDriven;\r\n";

	// controller
	public static String iController = "import org.springframework.stereotype.Controller;\r\n";
	public static String iRequestMapping = "import org.springframework.web.bind.annotation.RequestMapping;\r\n";
	public static String iResponseBody = "import org.springframework.web.bind.annotation.ResponseBody;\r\n";
	public static String iPathVariable="import org.springframework.web.bind.annotation.PathVariable;\r\n";

	public static String iGenerationType = "import javax.persistence.GenerationType;\r\n";// int型id使用
	public static String iSequenceGenerator = "import javax.persistence.SequenceGenerator;\r\n";// int型id使用
	public static String iGenericGenerator = "import org.hibernate.annotations.GenericGenerator;\r\n";// string型id使用
	public static String iSerializable = "import java.io.Serializable;\r\n";
	public static String iColumn = "import javax.persistence.Column;\r\n";
	public static String iEntity = "import javax.persistence.Entity;\r\n";
	public static String iId = "import javax.persistence.Id;\r\n";
	public static String iTable = "import javax.persistence.Table;\r\n";
	public static String iGeneratedValue = "import javax.persistence.GeneratedValue;\r\n";
	public static String iScope = "import org.springframework.context.annotation.Scope;\r\n";
	public static String iDate = "import java.util.Date;\r\n";
	public static String atiSequenceGenerator = "\t@SequenceGenerator(name = \"ID\", sequenceName = \"ID\")\r\n";
	public static String atiGeneratedValue = "\t@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"ID\")\r\n";
	public static String atsGeneratedValue = "\t@GeneratedValue(generator = \"system-uuid\")\r\n";
	public static String atsGenericGenerator = "\t@GenericGenerator(name = \"system-uuid\", strategy = \"uuid\")\r\n";

	public static String iService = "import org.springframework.stereotype.Service;\r\n";
	public static String iList = "import java.util.List;\r\n";
	public static String iMap = "import java.util.Map;\r\n";

	// swagger
	public static String iApiModelProperty = "import com.wordnik.swagger.annotations.ApiModelProperty;\r\n";
	public static String iApiModel = "import com.wordnik.swagger.annotations.ApiModel;\r\n";
	public static String iApiImplicitParam = "import com.wordnik.swagger.annotations.ApiImplicitParam;\r\n";
	public static String iApiImplicitParams = "import com.wordnik.swagger.annotations.ApiImplicitParams;\r\n";
	public static String iApiOperation = "import com.wordnik.swagger.annotations.ApiOperation;\r\n";

	// 自定义工具类
	public static String iModelBind = "import org.zsc.base.annotation.ModelBind;\r\n";
	public static String iModelPk = "import org.zsc.base.annotation.ModelPk;\r\n";
	public static String iModelDao = "import org.zsc.base.dao.ModelDao;\r\n";
	public static String iBusinessException = "import org.zsc.base.exception.BusinessException;\r\n";

}
