<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
	
	<s:if test="%{#session.idMenu.equals('30503')}">
		<s:url var="ajaxUpdateTitle" action="30503_title_" />
		<s:url var="ajaxUpdateButtons" action="30503_buttons" />
		<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
		<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	</s:if>
	
	
	<%-- Authentication --%>
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgEKTPReaderAuth" />
		<s:hidden name="idMenu" value="30503" />
		<s:hidden name="divName" value="divEKTPAuth" />
		<s:hidden name="deviceIP" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="divEKTPAuth" cssClass="ui-helper-hidden" />
	
	
	<s:form id="frm_E-KTP" name="frm_E-KTP" theme="css_xhtml" validate="false">
		<fieldset id="fsCommand" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.E-KTP.reader.fieldset.legend.command" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label id="lblIP" key="label.E-KTP.reader.field.device" />
							<s:select id="lstIP" list="mapIP" cssClass="ui-widget ui-widget-content"  />
						</td>
					</tr>
					<tr>
						<td>
							<sj:a id="btnInquiryEKTP" button="true">Inquiry EKTP</sj:a>
							<sj:a id="btnInquiryEKTPBypass" button="true">Inquiry EKTP Bypass</sj:a>
							<sj:a id="btnDummy" button="true" cssStyle="visibility:hidden">Dummy Button</sj:a>
							<sj:a id="btnResetBySpv" button="true" cssStyle="visibility:hidden">Reset Device State</sj:a>
							<sj:a id="btnRebootDevice" button="true" cssStyle="visibility:hidden">Restart Device</sj:a>
						</td>
					</tr>
					<tr>
						<td>
							<div id="ajaxResult"></div>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<fieldset id="fsE-KTP" class="ui-widget-content ui-corner-all">
			<legend class="ui-widget-header ui-corner-all"><s:text name="label.E-KTP.reader.fieldset.legend.data" /></legend>
			<table>
				<tr>
					<td colspan="6" align="center">
						<s:label id="lblProvince" key="label.blank" />
					</td>
				</tr>
				<tr>
					<td>
						<s:label id="lblTxtNIK" key="label.E-Ktp.inputNik" />
					</td>
					<td colspan="4">
						<s:label id="lblNIK" key="label.blank" />
					</td>
					<td id="imgPhoto" rowspan="11" align="center" style="vertical-align: middle" />
				</tr>
				
				<tr>
					<td colspan="5">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtName" key="label.E-KTP.reader.field.name" />
					</td>
					<td colspan="3">
						<s:label id="lblName" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtBirth" key="label.E-KTP.reader.field.birth" />
					</td>
					<td colspan="3">
						<s:label id="lblBirth" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtgender" key="label.E-Ktp.sex" />
					</td>
					<td style="min-width: 125px">
						<s:label id="lblGender" key="label.blank" />
					</td>
					<td>
						<s:label id="lblTxtBloodType" key="label.E-Ktp.bloodType" />
					</td>
					<td>
						<s:label id="lblBloodType" key="label.blank" />
					</td>
					
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtAddress" key="label.E-Ktp.address" />
					</td>
					<td colspan="3">
						<s:label id="lblAddress" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>
						<s:label id="lblTxtRT_RW" key="label.blank" />
					</td>
					<td colspan="3">
						<s:label id="lblRT_RW" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>
						<s:label id="lblTxtVillage" key="label.E-KTP.reader.field.village" />
					</td>
					<td colspan="3">
						<s:label id="lblVillage" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>
						<s:label id="lblTxtKecamatan" key="label.E-Ktp.namKec" />
					</td>
					<td colspan="3">
						<s:label id="lblKecamatan" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtReligion" key="label.E-Ktp.religion" />
					</td>
					<td colspan="3">
						<s:label id="lblReligion" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtMaritalStatus" key="label.E-KTP.reader.field.maritalStatus" />
					</td>
					<td colspan="3">
						<s:label id="lblMaritalStatus" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtProfession" key="label.E-Ktp.profession" />
					</td>
					<td colspan="3">
						<s:label id="lblProfession" key="label.blank" />
					</td>
					<td id="imgSignature" rowspan="3" align="center" style="vertical-align: middle" />
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtCitizenship" key="label.E-KTP.reader.field.citizenship" />
					</td>
					<td colspan="3">
						<s:label id="lblCitizenship" key="label.blank" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<s:label id="lblTxtValidUntil" key="label.E-KTP.reader.field.validUntil" />
					</td>
					<td colspan="3">
						<s:label id="lblValidUntil" key="label.blank" />
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<s:label id="lblCity" key="label.blank" />
					</td>
				</tr>
			</table>
		</fieldset>
	</s:form>
	
	<div id="divEKTPAuth" style="display:none"></div>
	<div id="divMessage" style="display:none"></div>
	
	<script type="text/javascript">
		function callRest(url, method, data_, success, error) {
			$("#ajaxResult").html("Requesting...");
			
		    var request = {
		          url: url
		        , type: method
		        , contentType: "application/x-www-form-urlencoded"
		        , cache: false
		        , dataType: 'json'
		        , headers: {
		            accept: 'application/json, text/html'
		          }
		        , data: data_
		        , timeout:120000
		        , success: success
		        , error: error
		    };
		    $.ajax(request);
		}
		
		function errorCallback(jqXHR, textStatus) {
			$("#ajaxResult").html("request error " + jqXHR.status + " - " + textStatus + " - " + jqXHR.responseText);
		}
		
		function clearResult() {
			$("#ajaxResult").html("");
			$("#frm_E-KTP").find("a[role='button']").removeClass("ui-state-hover");
		}
		
		function findEKTPElementById(elementId) {
			return $("#frm_E-KTP").find("#" + elementId);
		}
		
		function findEKTPLabel(labelName) {
			return $("#frm_E-KTP").find("label[for='" + labelName + "']");
		}
		
		function parseSelectedOfListIP() {
			var $selectedItem = findEKTPElementById("lstIP").find(":selected");
			var arr = $selectedItem.val().split("\|");
			var objIP = {
				ip: arr[0],
				protocol: arr[1]
			};
			
			return objIP;
		}
		
		function getEKTPReaderUserAuth(role, successFunction) {
			var objIP = parseSelectedOfListIP();
			
			$.post(
				"json/30503_execute.action",
				{action: role, deviceIP: objIP.ip},
				successFunction,
				"json"
			);
		}
		
		function renderToScreen(dataEKTP) {
			findEKTPLabel("lblProvince").text("PROVINSI " + dataEKTP.provinsi);
			findEKTPLabel("lblNIK").text(findEKTPLabel("lblNIK").text() + dataEKTP.nik);
			findEKTPLabel("lblName").text(findEKTPLabel("lblName").text() + dataEKTP.nama);
			findEKTPLabel("lblBirth").text(findEKTPLabel("lblBirth").text() + dataEKTP.tempatlahir + ", " + dataEKTP.tgllahir);
			findEKTPLabel("lblGender").text(findEKTPLabel("lblGender").text() + dataEKTP.gender);
			findEKTPLabel("lblBloodType").text(findEKTPLabel("lblBloodType").text() + dataEKTP.goldarah);
			findEKTPLabel("lblAddress").text(findEKTPLabel("lblAddress").text() + dataEKTP.alamat);
			findEKTPLabel("lblRT_RW").text(findEKTPLabel("lblRT_RW").text() + dataEKTP.rt + "/" + dataEKTP.rw);
			findEKTPLabel("lblVillage").text(findEKTPLabel("lblVillage").text() + dataEKTP.kelurahan);
			findEKTPLabel("lblKecamatan").text(findEKTPLabel("lblKecamatan").text() + dataEKTP.kecamatan);
			findEKTPLabel("lblReligion").text(findEKTPLabel("lblReligion").text() + dataEKTP.agama);
			findEKTPLabel("lblMaritalStatus").text(findEKTPLabel("lblMaritalStatus").text() + dataEKTP.maritalstatus);
			findEKTPLabel("lblProfession").text(findEKTPLabel("lblProfession").text() + dataEKTP.pekerjaan);
			findEKTPLabel("lblCity").text(findEKTPLabel("lblCity").text() + dataEKTP.kabupaten);
			findEKTPLabel("lblCitizenship").text(findEKTPLabel("lblCitizenship").text() + dataEKTP.kewarganegaraan);
			//findEKTPLabel("lblValidUntil").text(findEKTPLabel("lblValidUntil").text() + dataEKTP.nik);
			
			
			findEKTPElementById("imgPhoto").prepend("<img src=\"data:image/bmp;base64," + btoa(String.fromCharCode.apply(null, hexToBytes(dataEKTP.datafoto))) + "\" style=\"width: 85%\">");
			findEKTPElementById("imgSignature").prepend("<img src=\"data:image/bmp;base64," + btoa(String.fromCharCode.apply(null, hexToBytes(dataEKTP.data_ttd))) + "\" style=\"width: 85%\">");
			
			$("#frm_E-KTP").find("label[for^='lblTxt']").css("font-weight", "bold");
			findEKTPLabel("lblNIK").closest("tr")
				.add(findEKTPLabel("lblProvince").closest("tr"))
					.css("font-weight", "bold")
					.css("font-size", "larger");
		}
		
		function hexToBytes(hex) {
			bytes = [];
			for (var i=0; i<hex.length; i+=2)
				bytes.push(parseInt(hex.substr(i, 2), 16));
			return bytes;
		}
		
		function sendLog(dataLog) {
			$.post("30503_add.action", { log: dataLog });
		}
		
		function openAuthDialog() {
			var $current = $("#" + $("#frmDlgAuth_divName").val());
				var $clone = $current.clone();
				var $parent = $current.parent();
				var $prevSibling = $current.prev();
				
				if ($current.length) {
					$current
						.html("Please wait...")
						.dialog({
							autoOpen: false,
							modal: true,
							resizable: false
						})
						.dialog("option", "position", "center")
						.dialog("option", "width", "320")
						.dialog("option", "height", "180")
						.dialog("option", "title", "Supervisor EKTP Reader")
						.unbind("dialogopen")
						.bind("dialogopen", function(event) {
							var objIP = parseSelectedOfListIP();
							$("#frmDlgAuth_deviceIP").val(objIP.ip);
							$("#aDlgAuth").click();
						})
						.unbind("dialogclose")
						.bind("dialogclose", function(event) {
							$(this).dialog("destroy").remove();
							
							if ($prevSibling.length)
								$prevSibling.after($clone);
							else
								$parent.append($clone);
						})
						.dialog("open");
				}
		}
		
		
				
		jQuery(document).ready(function() {
			$("#frm_E-KTP").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("#frm_E-KTP").find(".wwgrp").css("height", "15px");
			$("#frm_E-KTP > fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.css("width", function() {
					var width_ = ((parseInt($(this).css("width"), 10) + 25) + "px");
					return width_;
				});
			
			/* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */ 
			if (navigator.appName.indexOf("Internet Explorer") > -1) {
				$("#frm_E-KTP").find("*[role='button']").css("position", "static");
				$("#ph-main > form[action$='dlg.action']").css("display", "none");
			}
			
			findEKTPElementById("imgPhoto").append(findEKTPLabel("lblCity").parent().detach());
			findEKTPElementById("wwlbl_lblIP").append("&nbsp;").append("&nbsp;").append($("#lstIP").detach());
			var $lblTxtRT_RW = findEKTPLabel("lblTxtRT_RW");
			$lblTxtRT_RW.html('<s:text name="label.E-Ktp.rt" />/<s:text name="label.E-Ktp.rw" />' + $lblTxtRT_RW.html());
			
			// remove character ':'
			$("#frm_E-KTP").find("label").each(function() {
				var value = $(this).html().trim();
				$(this).html(value.substring(0, value.length - 1));
				$(this).css("font-style", "normal");
			});
			
			if ("${session.idMenu}" == "30503") {
				if (($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
					$("#rbBS").buttonset("destroy");
					$("#tempTitle").click();
					$("#tempButtons").click();
				}
				else {
					$("#rbBS").data("rdb").callCurrent();
					if ($("#actionError").length == 0)
						$("#rbBS").data("rdb").clear(false);
				}
			}
			
			
			var url = '<s:property value="baseResourceURI" />';
			var protocolIPs = $.parseJSON('<s:property value="baseProtocols" escapeHtml="false" />');
			var ektpOprAuth = {};
			 
			$("#btnInquiryEKTP").click(function(event) {
				var $btn = $(this).button("disable");
				findEKTPElementById("lstIP").attr("disabled", true);
				// Get Operator EKTP authentification
				getEKTPReaderUserAuth(
					"OPR",
					function(data) {
						ektpOprAuth = data;
						var objIP = parseSelectedOfListIP();
						var strURL = url
								.replace("{protocol}", objIP.protocol)
								.replace("{ip}", objIP.ip)
								.replace("{port}", protocolIPs[objIP.protocol])
								.concat("/bacaChip");
						
						callRest(
							strURL, 
							"post",
							{username: ektpOprAuth.EKTPOprUser, password: ektpOprAuth.EKTPOprPassword},
							function(resultData) {
								clearResult();
								
								if (resultData.errornumber !== 'undefined') {
									if (resultData.errornumber == '0') {
										renderToScreen(resultData);
										//findEKTPElementById("ajaxResult").html(JSON.stringify(resultData));
										var log = $selectedItem.text() + "^~" + ip + "^~" + "1" + "^~" + JSON.stringify(resultData);
										sendLog(log);
									}
									else if ((resultData.errornumber == null) && (resultData.messagereturn == null)) {
										messageBox(1, "Response null", null);
									}
									else if (resultData.messagereturn.lastIndexOf("Verifikasi sidik jari gagal") == 0) {
										messageBox(1, resultData.messagereturn, function() { findEKTPElementById("btnInquiryEKTPBypass").button("enable"); });
									}
									else if (resultData.messagereturn.lastIndexOf("Alat sedang digunakan") == 0) {
										messageBox(1, resultData.messagereturn, function() { findEKTPElementById("btnResetBySpv").css("visibility", "visible"); });
									}
									else {
										findEKTPElementById("ajaxResult").html("error: " + resultData.messagereturn);
										$btn.button("enable");
									}
								}
								else {
									findEKTPElementById("ajaxResult").html("error: " + resultData);
									$btn.button("enable");
								}
							},
							errorCallback
						);
					} 
				);
			});
			
			$("#btnInquiryEKTPBypass").click(function(event) {
				var $btn = $(this);
				
				dlgParams = {};
				dlgParams.event = event;
				dlgParams.onSubmit = function() {
					$btn.button("disable");
					// Get Supervisor EKTP authentification
					getEKTPReaderUserAuth(
						"SPV",
						function(data) {
							var objIP = parseSelectedOfListIP();
							var strURL = url
									.replace("{protocol}", objIP.protocol)
									.replace("{ip}", objIP.ip)
									.replace("{port}", protocolIPs[objIP.protocol])
									.concat("/byPassSidikJari");
							
							callRest(
								strURL,
								"post", 
								{nrp: data.EKTPSpvUser, pin: data.EKTPSpvPassword, username: ektpOprAuth.EKTPOprUser, password: ektpOprAuth.EKTPOprPassword},
								function(resultData) {
									clearResult();
									
									if (resultData.errornumber != undefined) {
										if (resultData.errornumber == '0') {
											$btn.button("disable");
											clearResult();
											
											renderToScreen(resultData);
											//findEKTPElementById("ajaxResult").html(JSON.stringify(resultData));
											$btn.button("disable");
											
											var log = $selectedItem.text() + "^~" + ip + "^~" + "2" + "^~" + JSON.stringify(resultData);
											sendLog(log);
										}
										else {
											findEKTPElementById("ajaxResult").html("error: " + resultData.messagereturn);
											$btn.button("enable");
										}
									}
									else {
										findEKTPElementById("ajaxResult").html("error: " + resultData);
										$btn.button("enable");
									}
								},
								errorCallback
							);
						} 
					);
				};
				
				openAuthDialog();
			});
			
			$("#btnResetBySpv").click(function(event) {
				dlgParams = {};
				dlgParams.event = event;
				dlgParams.onSubmit = function() {
					// Get Supervisor EKTP authentification
					getEKTPReaderUserAuth(
						"SPV",
						function(data) {
							var objIP = parseSelectedOfListIP();
							var strURL = url
									.replace("{protocol}", objIP.protocol)
									.replace("{ip}", objIP.ip)
									.replace("{port}", protocolIPs[objIP.protocol])
									.concat("/resetBySpv");
							
							callRest(
								strURL,
								"post", 
								{username: data.EKTPSpvUser, password: data.EKTPSpvPassword},
								function(resultData) {
									clearResult();
									
									if (resultData.errornumber != undefined) {
										if (resultData.errornumber == '0')
											messageBox(1, resultData.messagereturn, null);
										else
											messageBox(1, resultData.messagereturn, function() { findEKTPElementById("btnRebootDevice").css("visibility", "visible"); });
									}
									else
										findEKTPElementById("ajaxResult").html("error: " + resultData);
								},
								errorCallback
							);
						} 
					);
				};
				
				openAuthDialog();
			});
			
			$("#btnRebootDevice").click(function(event) {
				dlgParams = {};
				dlgParams.event = event;
				dlgParams.onSubmit = function() {
					// Get Supervisor EKTP authentification
					getEKTPReaderUserAuth(
						"OPR",
						function(data) {
							var objIP = parseSelectedOfListIP();
							var strURL = url
									.replace("{protocol}", objIP.protocol)
									.replace("{ip}", objIP.ip)
									.replace("{port}", protocolIPs[objIP.protocol])
									.concat("/rebootDevice");
							
							callRest(
								strURL,
								"post", 
								{username: data.EKTPOprUser, password: data.EKTPOprPassword},
								function(resultData) {
									clearResult();
									
									if (resultData.errornumber != undefined)
										messageBox(1, resultData.messagereturn, null);
									else
										findEKTPElementById("ajaxResult").html("error: " + resultData);
								},
								errorCallback
							);
						} 
					);
				};
				
				openAuthDialog();
			});
			
			
			findEKTPElementById("btnInquiryEKTP").button("enable");
			findEKTPElementById("btnInquiryEKTPBypass").button("disable");
		});
	</script>
	
</s:if>