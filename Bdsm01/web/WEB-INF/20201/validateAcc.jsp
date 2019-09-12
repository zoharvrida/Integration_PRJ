<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<script type="text/javascript">
    $(function() {
        $("#" + tempParams.flgAcct).val('<s:property value="%{flgAcct}" />');
        if($("#" + tempParams.flgAcct).val() == '2') {
            alert('CIF ' + $("#" + tempParams.noAccount).val().substr(4, $("#" + tempParams.noAccount).val().length) + ' not found');
        } else if($("#" + tempParams.flgAcct).val() == '3') {
            alert('Account ' + $("#" + tempParams.noAccount).val() + ' not found');
        } else if($("#" + tempParams.flgAcct).val() == '4') {
            alert('Invalid CIF : ' + $("#" + tempParams.noAccount).val().substr(4, $("#" + tempParams.noAccount).val().length));
        } else if($("#" + tempParams.flgAcct).val() == '5') {
            alert('Invalid Account : ' + $("#" + tempParams.noAccount).val());
        }
    });
</script>