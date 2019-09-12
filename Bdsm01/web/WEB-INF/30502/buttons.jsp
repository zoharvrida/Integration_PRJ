<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sj:radio
    id="rbBS"
    list="{'Inquiry', 'Clear'}"
    name="rbBS" disabled="true"
/>
<script type="text/javascript">
function clearResult() {
    $("#tabs1").tabs("option", "selected", 0);
    $("#gridtable_pager_left #note").remove();
    var model = $("#gridtable").data("List");
    $("#gridtable").data("List", model);
    $("#gridtable").jqGrid("clearGridData", {
        clearfooter: true
    }).trigger("reloadGrid");
    $("#frmMain_model_nik").attr("value", null);
    $("#frmMain_model_name").attr("value", null);
    $("#frmMain_model_ektpStatus").attr("value", null);
    $("#frmMain_model_birthPlace").attr("value", null);
    $("#frmMain_model_dob").attr("value", null);
    $("#frmMain_model_sex").attr("value", null);
    $("#frmMain_model_bloodType").attr("value", null);
    $("#frmMain_model_address").attr("value", null);
    $("#frmMain_model_rt").attr("value", null);
    $("#frmMain_model_rw").attr("value", null);
    $("#frmMain_model_namKel").attr("value", null);
    $("#frmMain_model_namKec").attr("value", null);
    $("#frmMain_model_namKab").attr("value", null);
    $("#frmMain_model_namProp").attr("value", null);
    $("#frmMain_model_posCode").attr("value", null);
    $("#frmMain_model_religion").attr("value", null);
    $("#frmMain_model_marStat").attr("value", null);
    $("#frmMain_model_profession").attr("value", null);
    $("#frmMain_model_lastEdu").attr("value", null);
    $("#frmMain_model_namFather").attr("value", null);
    $("#frmMain_model_namMother").attr("value", null);
    $("#frmMain_model_nokk").attr("value", null);
    $("#frmMain_inputKk").focus();
}
jQuery(document).ready(function() {
    $(window).resize();
    rdb.resetDisabled();
    rdb.resetReadonly();
    
    rdb.setDisabled("frmMain_inputKk", "0111");
    rdb.setDisabled("frmMain_model_nik"  , "1111");
    rdb.setDisabled("frmMain_model_name"  , "1111");
    rdb.setDisabled("frmMain_model_ektpStatus"  , "1111");
    rdb.setDisabled("frmMain_model_birthPlace"  , "1111");
    rdb.setDisabled("frmMain_model_dob"  , "1111");
    rdb.setDisabled("frmMain_model_sex"  , "1111");
    rdb.setDisabled("frmMain_model_bloodType"  , "1111");
    rdb.setDisabled("frmMain_model_address"  , "1111");
    rdb.setDisabled("frmMain_model_rt"  , "1111");
    rdb.setDisabled("frmMain_model_rw"  , "1111");
    rdb.setDisabled("frmMain_model_namKel"  , "1111");
    rdb.setDisabled("frmMain_model_namKec"  , "1111");
    rdb.setDisabled("frmMain_model_namKab"  , "1111");
    rdb.setDisabled("frmMain_model_namProp"  , "1111");
    rdb.setDisabled("frmMain_model_posCode"  , "1111");
    rdb.setDisabled("frmMain_model_religion"  , "1111");
    rdb.setDisabled("frmMain_model_marStat"  , "1111");
    rdb.setDisabled("frmMain_model_profession"  , "1111");
    rdb.setDisabled("frmMain_model_lastEdu"  , "1111");
    rdb.setDisabled("frmMain_model_namFather"  , "1111");
    rdb.setDisabled("frmMain_model_namMother"  , "1111");
    rdb.setDisabled("frmMain_model_nokk"  , "1111");

    rdb.setReadonly("frmMain_inputKk", "0111");
    rdb.setReadonly("frmMain_model_nik"  , "1111");
    rdb.setReadonly("frmMain_model_name"  , "1111");
    rdb.setReadonly("frmMain_model_ektpStatus"  , "1111");
    rdb.setReadonly("frmMain_model_birthPlace"  , "1111");
    rdb.setReadonly("frmMain_model_dob"  , "1111");
    rdb.setReadonly("frmMain_model_sex"  , "1111");
    rdb.setReadonly("frmMain_model_bloodType"  , "1111");
    rdb.setReadonly("frmMain_model_address"  , "1111");
    rdb.setReadonly("frmMain_model_rt"  , "1111");
    rdb.setReadonly("frmMain_model_rw"  , "1111");
    rdb.setReadonly("frmMain_model_namKel"  , "1111");
    rdb.setReadonly("frmMain_model_namKec"  , "1111");
    rdb.setReadonly("frmMain_model_namKab"  , "1111");
    rdb.setReadonly("frmMain_model_namProp"  , "1111");
    rdb.setReadonly("frmMain_model_posCode"  , "1111");
    rdb.setReadonly("frmMain_model_religion"  , "1111");
    rdb.setReadonly("frmMain_model_marStat"  , "1111");
    rdb.setReadonly("frmMain_model_profession"  , "1111");
    rdb.setReadonly("frmMain_model_lastEdu"  , "1111");
    rdb.setReadonly("frmMain_model_namFather"  , "1111");
    rdb.setReadonly("frmMain_model_namMother"  , "1111");
    rdb.setReadonly("frmMain_model_nokk"  , "1111");
    
    rdb.setDisableButton("btnFind", "0111");
    
    rdb.setOnChange(
        function() {
            changeAction("frmMain", "30502", "");
            try{
            $("#frmMain_inputKk").focus();
            }catch(err){

            }
        },
        null,
        null,
        null,
        function(removeMsg) {
            $("#frmMain_inputKk").attr("value", null);
            clearResult();
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
                $("#spinner").addClass("ui-helper-hidden");
                $("#errMsg").html("");
            }
        }
    );

    rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1000');
});
</script>