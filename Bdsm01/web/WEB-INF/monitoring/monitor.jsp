<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
    $(function() {
        if (tempParams.onClose != undefined) {
            tempParams.onClose('<s:property value="%{availCpu}" />',
            '<s:property value="%{osName}" />',
            '<s:property value="%{osVer}" />',
            '<s:property value="%{osArch}" />');
        }
    });
</script>