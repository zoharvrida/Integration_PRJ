<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<table>
<tbody>
<tr><td>
<sj:checkboxlist id="rbBS" list="{'clear'}" name="rbBS" />
</td>

                    <td id="spinCon" colspan="5" width="1000px" >&nbsp;
                        <span id="spinner" class="ui-helper-hidden" style="position:relative; float:right; margin-right:30px;">Loading ...<img src='./spinner1.gif' style='position:absolute; top:0; width:25px; height:25px; z-index:99' /></span>
                    </td> 
                </tr>
				</tbody>
				</table>
<script type="text/javascript">
   
    function clearResult() {
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
    }
    jQuery(document).ready(function() {
        /* $("#rbBS").html("&nbsp;"); */
        $(window).resize();
        rdb.resetDisabled();
        rdb.resetReadonly();
		//$("#spinner").removeClass("ui-helper-hidden");
		$("#spinner").addClass("ui-helper-hidden");
        //$("#divMessage").remove();
        $("#divMessage").dialog("close");
        $("#divMessage").remove();
        if($("#frmMain_flagButton").val() == "1"){
            $("#rbBS").hide();
        }
        rdb.setDisableButton("btnFind", "0111");
        rdb.setOnChange(function() {
            $("#frmMain_customerName").focus();
            changeAction("frmMain", "26301", "_execute");

        },
        null,
        null,
        null,
        function(removeMsg) {
            if($("#frmMain_flagButton").val() == "0"){
                if($("#frmMain_gridClear").val() == "2"){
                    $("#frmGo_idMenu").val("26301");
                    $("#frmGo_goAction").val("exec");
                    $("#btnGo").click();
                }
            }
            if (removeMsg) {
                $("#actionMessage").remove();
                $("#actionError").remove();
                $("span.errorMessage").remove();
                $("label").removeClass("errorLabel");
                $("#actionMessage").remove();
                $("#spinner").addClass("ui-helper-hidden");
                $("#errMsg").html("");
            }
        }
    );
	$('#rbBS-1').click(function() {
            if($("#frmMain_flagButton").val() == "0"){
                if($("#frmMain_gridClear").val() == "2"){
                    //alert("REMOVE ALL");
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
					$("#actionMessage").remove();
					$("#spinner").addClass("ui-helper-hidden");
					$("#errMsg").html("");
					
					//alert("REFRESH ALL");
					$("#frmGo_idMenu").val("26301");
                    $("#frmGo_goAction").val("exec");
                    $("#btnGo").click();
                } 
            }
			$("#rbBS-1").attr("checked", false).button("refresh"); 
	});

        rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1000');
    });
</script>