<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<sj:radio 
	id="rbBS" 
	list="{'Inquiry', 'Clear'}" 
	name="rbBS"
	disabled="true" 
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		rdb.resetDisabled();
		rdb.resetReadonly();
		rdb.resetDisableButton();
		
		$(window).resize();
		
		rdb.setOnChange(
			function() {
				findEKTPElementById("lstIP").focus();
			},
			null,
			null,
			null,
			function(removeMsg) {
				findEKTPElementById("lstIP").removeAttr("disabled");
				findEKTPElementById("btnInquiryEKTP").button("enable");
				findEKTPElementById("btnInquiryEKTPBypass").button("disable");
				findEKTPElementById("btnResetBySpv").css("visibility", "hidden");
				findEKTPElementById("btnRebootDevice").css("visibility", "hidden");
				findEKTPElementById("ajaxResult").html("");
				
				findEKTPLabel("lblProvince").text("");
				findEKTPLabel("lblNIK").text(": ");
				findEKTPLabel("lblName").text(": ");
				findEKTPLabel("lblBirth").text(": ");
				findEKTPLabel("lblGender").text(": ");
				findEKTPLabel("lblBloodType").text(": ");
				findEKTPLabel("lblAddress").text(": ");
				findEKTPLabel("lblRT_RW").text(": ");
				findEKTPLabel("lblVillage").text(": ");
				findEKTPLabel("lblKecamatan").text(": ");
				findEKTPLabel("lblReligion").text(": ");
				findEKTPLabel("lblMaritalStatus").text(": ");
				findEKTPLabel("lblProfession").text(": ");
				findEKTPLabel("lblCity").text("");
				findEKTPLabel("lblCitizenship").text(": ");
				findEKTPLabel("lblValidUntil").text(": ");
				
				findEKTPElementById("imgPhoto").find("img").remove();
				findEKTPElementById("imgSignature").find("img").remove();
				
				$("#frm_E-KTP").find("label[for^='lblTxt']").css("font-weight", "");
				findEKTPLabel("lblNIK").closest("tr")
					.add(findEKTPLabel("lblProvince").closest("tr"))
						.css("font-weight", "")
						.css("font-size", "");
			
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
				}
			}
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1001');
	});
</script>