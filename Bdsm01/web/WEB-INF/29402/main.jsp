<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
    <script type="text/javascript" src="_js/pdf/fpdf.bundled.js"></script>

    <s:url var="ajaxUpdateTitle" action="29402_title_" />
    <s:url var="ajaxUpdateButtons" action="29402_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />

    <s:form id="formRef" action="29402_inquiryRef.action">
        <s:hidden name="refNetworkNo" />
        <s:hidden name="codAccNo" />
        <s:hidden name="nameCurrency" />
        <s:hidden name="amtTxnAcy" />
        <s:hidden name="amtTxnTcy" />
        <s:hidden name="currencyCode" />
        <s:hidden name="destAmount"/>
        <s:hidden name="sellRate" />
		<s:hidden name="codAcctTitle"/>
        <s:token name="refToken"/>
    </s:form>
    <sj:a id="tempformRef" formIds="formRef" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>

    <s:form id="formRate" action="29402_retriveRateDestination.action">
        <s:hidden name="refNetworkNo" />
        <s:hidden name="currencyCode" />
        <s:hidden name="sellrate" />
        <s:token name="rateToken" />
    </s:form>
    <sj:a id="tempformRate" formIds="formRate" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>

    <s:form id="formSave" action="29402_add.action">
        <s:hidden name="refNetworkNo" />
        <s:hidden name="codAccNo" />
        <s:hidden name="nameCurrency" />
        <s:hidden name="amtTxnAcy" />
        <s:hidden name="amtTxnTcy" />
        <s:hidden name="currencyCode"/>
        <s:hidden name="destAmount" />
		<s:hidden name="sellRate"/>
        <s:token name="saveToken" />
    </s:form>
    <sj:a id="tempformSave" formIds="formSave" targets="ph-main" cssClass="ui-helper-hidden"></sj:a>

    <%-- Lookup 1. Original Currency --%>
    <s:form id="frmDlgOriCcyInq" action="dlg">
        <s:hidden name="dialog" value="dlgOriCcy" />
        <s:hidden name="master" value="originalCurrency" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgOriCcyInq" formIds="frmDlgOriCcyInq" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <%-- Lookup Currency Code Inquiry (destination) --%>
    <s:form id="frmDlgCCodeRateInq" action="dlg">
        <s:hidden name="dialog" value="dlgCCodeRate" />
        <s:hidden name="master" value="multiCurrencyRemittance" />
        <s:hidden name="term"   value=""/>
        <s:token />
    </s:form>
    <sj:a id="aDlgCCodeRateInq" formIds="frmDlgCCodeRateInq" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <s:form id="frmMain" name="frmMain" action="29402_execute" theme="css_xhtml">
        <fieldset id="fsInquiry" class="ui-widget-content ui-corner-all" style="border: 0px">
            <legend class="ui-widget-header ui-corner-all"><s:text name="label.mcr.fieldset.legend.trx" /></legend>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <s:label id="lblTrxRefNoInq" key="label.mcr.transaksi.refno" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield
                                name="refNetworkNo" 
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblActNo" key="label.mcr.account.no" requiredLabel="flase" />
                        </td>
                        <td>
                            <s:textfield
                                name="codAccNo" 
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                readonly="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
					<tr>
                        <td>
                            <s:label id="lblCusTitle" key="label.mcr.customer.tittle" requiredLabel="false" style="display: none" />
                        </td>
                        <td>
                            <s:textfield
                                name="codAcctTitle"
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                readonly="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblOriCcyInq" key="label.mcr.original.currency" requiredLabel="flase" />
                        </td>
                        <td>
                            <s:textfield
                                name="nameCurrency" 
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                readonly="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>              
                    <tr>
                        <td>
                            <s:label id="lblOriAmount" key="label.mcr.original.amount" requiredLabel="flase" />
                        </td>
                        <td>
                            <s:textfield
                                name="amtTxnAcy" 
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                readonly="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblUsdEquival" key="label.mcr.usd.equivalent" requiredLabel="false" />
                        </td>
                        <td>
                            <s:textfield 
                                name="amtTxnTcy"
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                readonly="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDestCcyNamInq" key="label.mcr.destination.currencyname" requiredLabel="true" />
                        </td>
                        <td>
                            <s:textfield
                                name="currencyCode" 
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                />
                            <s:hidden name="FlagCode" disabled="true" />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDestCcyRate" key="label.mcr.destination.currencyrate" requiredLabel="false" />
                        </td>
                        <td>
                            <s:textfield 
                                name="sellRate"
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="this.value=this.value.toUpperCase();"
                                readonly="true"
                                />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:label id="lblDestAmount" key="label.mcr.destination.amount" requiredLabel="false" />
                        </td>
                        <td>
                            <s:textfield 
                                name="destAmount"
                                size="20"
                                maxlength="40"
                                cssClass="ui-widget ui-widget-content"
                                readonly="true"
                                />
                        </td>
                    <tr>
                        <td>
                            <sj:a id="btnCalc" button="true">Calculate</sj:a>
                            </td>
                            <td>
                            <sj:submit id="btnSave"
                                       buttonIcon="ui-icon-gear"
                                       button="true"
                                       key="Save"
                                       disabled="false"
                                       targets="ph-main"
                                       />
                        </td>
                        <td>
							<sj:a id="btnGeneratePDF" button="true" onclick="if ($('#btnGeneratePDF').hasClass('ui-button-disabled') == false) generatePDF();"><s:text name="button.generate.pdf" /></sj:a>							
                        </td>                     
                    </tr>
                </tbody>
            </table>
            <s:token />
            <hr class="ui-widget-content" />
        </fieldset>
    </s:form>
    <div id="divLoadingCalc" />
    <%-- Buttons 
    <sj:a id="btnLookupOriCcyInq" button="true">...</sj:a> --%>
    <sj:a id="btnSearchInq" button="true">Search</sj:a> 
    <sj:a id="btnLookupDestCcyInq" button="true">...</sj:a>


    <script type="text/javascript">
        function getLabelText(elementId) {
			return $("label[for='" + elementId + "']").text().replace(/[*:]/g, '').trim();
		}
        function getDataText(elementId, appendText) {
			var $elem = $("#" + elementId);
			var value = "";
			if ($elem.length == 1) {
				if (($elem.prop("tagName") == "INPUT") && ($elem.prop("type") == "text"))
					value = $("#" + elementId).val().trim();
				else if ($elem.prop("tagName") == "TEXTAREA")
					value = $("#" + elementId).val().trim();
				else if ($elem.prop("tagName") == "SELECT")
					value = $("#" + elementId + " option:selected").text().trim();
			}
			if (arguments.length > 1)
				value += (" " + appendText.trim());
			return (value == "")? "-": value;
		}
		function rgbToHex(arrRGB) {
			var result = "";
			var temp;
			for (i in arrRGB) {
				temp = Number(arrRGB[i]).toString(16).trim();
				if (temp.length == 1)
					temp = "0" + temp;
				result += temp;
			}
			return result;
		}
		var FPDRNewLegend = function(fieldsetId) {
			var obj = 
				FPDF('div')
					.text($("#" + fieldsetId + "> legend").text())
					.css({
						padding:2,
						marginBottom: 2,
						background: rgbToHex($("#ph-title").css("border-top-color").replace(/[rgb\(\)]/g, '').split(',')),
						color: rgbToHex($("#ph-title").css("color").replace(/[rgb\(\)]/g, '').split(',')),
						fontStyle: 'italic',
						borderRadius: 1.4
					});
			return obj;
		};
		var FPDFNewRow = function(labelId, dataId, appendText) {
			var isLabel = (labelId != null);
			var obj = 
				FPDF('flexbox')
					.css({fontSize: 11, lineHeight: 2.0, width: 200, marginLeft: 5})
					.append(
						FPDF('div')
							.css({width: 95})
							.text(isLabel? getLabelText(labelId): " ")
					)
					.append(
						FPDF('div')
							.css({width: 5})
							.text(isLabel? ":": "")
					)
					.append(
						FPDF('div')
							.css({width: 105, fontWeight: 'bold', fontSize: 12})
							.text((arguments.length > 2)? getDataText(dataId, appendText): getDataText(dataId))
					);
			return obj;
		};
		var FPDFBlankRow = function() {
			var obj = 
				FPDF('flexbox')
					.css({fontSize: 9, lineHeight: 1.5, width: 150, marginLeft: 5})
					.append(
						FPDF('div').text("")
					);
			return obj;
		};
		function generatePDF() {
			if (window.ActiveXObject || "ActiveXObject" in window) { // IE
				generatePDF().save("MultyCurrencyRemittance.pdf");
			}
			else {
				var newWindow = window.open('', 'Print', 'width=800, height=600');
				var frame = newWindow.document.createElement('iframe');
				frame.id = 'fraPrint';
				frame.width = '100%';
				frame.height = '100%';
				newWindow.document.body.appendChild(frame);
				frame.src = newWindow.opener.generatePDFContent().toDataUri();
			}
		}
		function generatePDFContent() {
			var MyDoc = FPDF.Doc.extend({
				Page: FPDF.Page.extend({
					defaultCss: {
						padding:[30,18,20,24],
						lineHeight:1.4
					},
					//header: function(){
						//var nowDate = new Date();
						//var date = nowDate.getDate();
						//var month = nowDate.getMonth() + 1;
						//var year = nowDate.getFullYear();
						//var hours = nowDate.getHours();
						//var minutes = nowDate.getMinutes();
						//var seconds = nowDate.getSeconds();
						//var format2digits = function(input) {
							//return (((input < 10)? "0": "") + input);
						//};
						//this.css({
								//top: 15,
								//fontSize: 9,
								//textAlign: 'right'
							//})
							//.text(format2digits(date) + '-' + format2digits(month) + "-" + year + " " + 
									//format2digits(hours) + ":" + format2digits(minutes) + ":" + format2digits(seconds));
					//},
					footer: function(){
						this.css({
								top: 280,
								fontSize: 8,
								padding: 1,
								textAlign: 'center'
							})
							.text(function() {
								return 'PT. Bank Danamon Indonesia , Tbk.';
							});
					}
				})
			});
			var doc = new MyDoc({format:'a4'});
			doc.css({
			});
			doc.title("Multi Currency Remittance");
			doc.author("Bank Danamon Supplementary Module (BDSM)");
			//var paymentDateRange = (($("#frmMain_paymentMinDate").closest("td").css("display") == "none") == false);
			var pages = [""];
			var onePage = function(index) {
				var nowDate = new Date();
				var date = nowDate.getDate();
				var month = nowDate.getMonth() + 1;
				var year = nowDate.getFullYear();
				var hours = nowDate.getHours();
				var minutes = nowDate.getMinutes();
				var seconds = nowDate.getSeconds();
				var format2digits = function(input) {
				return (((input < 10)? "0": "") + input);
				};
				var obj = 
					FPDF('div')
						.append(
							FPDF('div')
								.text("Multi Currency Remittance")
								.css({
									fontSize: 14,
									lineHeight: 1,
									fontStyle: 'bold',
									fontFamily: 'times',
									color: '000000',
									textAlign: 'center'
								})
						)
						.append(
							FPDF('div')
								.text("")
								.css({width: 60})
						)
						.append(
							FPDF('div')
								.text("Transaksi Multi Currency Remittance Anda telah berhasil diproses.")
								.css({
									fontSize: 11,
									lineHeight: 1,
									fontFamily: 'times',
									marginBottom: 2,
									color: '000000',
									textAlign: 'left'
								})
						)
						.append(
							FPDF('div')
								.text("Berikut adalah rincian transaksi Multi Currency Remittance:")
								.css({
									fontSize: 11,
									lineHeight: 0.1,
									fontFamily: 'times',
									marginBottom: 5,
									color: '000000',
									textAlign: 'left'
								})
						)
						.append(FPDRNewLegend("fsInquiry"))
						.append(FPDFNewRow("lblTrxRefNoInq", "frmMain_refNetworkNo"))
						.append(FPDFNewRow("lblActNo", "frmMain_codAccNo"))
						.append(FPDFNewRow("lblCusTitle", "frmMain_codAcctTitle"))
						.append(FPDFNewRow("lblOriCcyInq", "frmMain_nameCurrency"))
						.append(FPDFNewRow("lblOriAmount", "frmMain_amtTxnAcy"))
						.append(FPDFNewRow("lblUsdEquival", "frmMain_amtTxnTcy"))
						.append(FPDFNewRow("lblDestCcyNamInq", "frmMain_currencyCode"))
						.append(FPDFNewRow("lblDestCcyRate", "frmMain_sellRate"))
						.append(FPDFNewRow("lblDestAmount", "frmMain_destAmount"))
						.append(FPDFBlankRow())
						// more blank line
						.append(
							FPDF('flexbox')
								.css({fontSize: 9, lineHeight: 1.5, width: 150, marginTop: 3, marginBottom: 3})
						)
						.append(
							FPDF('div')
								.text("Disclaimer :")
								.css({
									fontSize: 15,
									lineHeight: 1,
									fontStyle: 'italic',
									fontFamily: 'times',
									color: '000000',
									textAlign: 'left'
								})
						).append(
							FPDF('div')
								.text("")
						)
						.append(
							FPDF('div')
								.text("- Hasil perhitungan di atas hanya sebagai indikator atas jumlah transaksi keuangan yang akan dilakukan")
								.css({
									fontSize: 10,
									lineHeight: 2,
									fontStyle: 'italic',
									fontFamily: 'times',
									color: '000000',
									textAlign: 'justify'
								})
						)
						.append(
							FPDF('div')
								.text("- Nominal transaksi keuangan akan diproses berdasarkan effective rate (nilai tukar) yang berlaku pada saat transaksi dijalankan oleh bank")
								.css({
									fontSize: 10,
									lineHeight: 2,
									fontStyle: 'italic',
									fontFamily: 'times',
									color: '000000',
									textAlign: 'justify'
								})
						)
						.append(
							FPDF('div')
								.text("- Informasi ini hasil cetakan komputer dan tidak memerlukan tanda tangan pejabat bank")
								.css({
									fontSize: 10,
									lineHeight: 2,
									fontFamily: 'times',
									fontStyle: 'italic',
									color: '000000',
									textAlign: 'justify'
								})
						)
						.append(
							FPDF('flexbox')
								.css({fontSize: 9, lineHeight: 1.5, width: 195, height: 30})
								.append(
									FPDF('div')
										.css({width: 50})
								)
						)
						// signature
						.append(
							FPDF('flexbox')
								.css({fontSize: 10, lineHeight: 1.5, width: 195, height: 30, fontStyle: 'italic'})
								.append(
									FPDF('div')
									.css({width: 45, marginLeft: 0.9, textAlign: 'center'})
									.text("Tanggal cetak :" + format2digits(date) + '-' + format2digits(month) + "-" + year + " " + 
											format2digits(hours) + ":" + format2digits(minutes) + ":" + format2digits(seconds))
								)
								//.append(
									//FPDF('div')
										//.css({width: 50})
								//)
								.append(
									FPDF('div')
										.css({width: 70, marginLeft: 10, textAlign: 'center'})
										.text('Tanda Tangan Nasabah')
								)
								.append(
									FPDF('div').css({width: 0})
								)
						)
						.append(FPDF('flexbox')
							.css({fontSize: 9, lineHeight: 1.5, width: 150, height: 0.1})
							.append(
								FPDF('div').css({width: 92})
							)
							.append(
								FPDF('div')
									.css({width: 42})
							)
							.append(
								FPDF('div')
									.css({width: 57, borderWidth: 0.3})
									.text('')
							)
							.append(
								FPDF('div').css({width: 19})
							)
						)
						.preventSplitting()
					;
				return obj;
			};
			for (var i=0; i<pages.length; i++) {
				doc.append(onePage(i));
			}
			return doc;
		}
        jQuery(document).ready(function() {
            /* === [BEGIN] alter display === */
            $("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
            /*$("#frmMain_namCurrencyDesc").parent().append('&nbsp;').append($("#btnLookupOriCcyInq").detach());**/
            $("#frmMain_refNetworkNo").parent().append('&nbsp;').append($("#btnSearchInq").detach());        
            $("#frmMain_currencyCode").parent().append('&nbsp;').append($("#btnLookupDestCcyInq").detach());
			$("#btnSave").parent().append('&nbsp;').append($("#btnGeneratePDF").detach());
			$("#btnGeneratePDF").hide();
			$("#frmMain_codAcctTitle").closest("tr").hide(); //hiden field on screen
            //tittle 29402
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
                
            //
        
            function rateDes() {
                $('#formRate_refNetworkNo').attr('value',$('#frmMain_refNetworkNo').val());
                $('#formRate_currencyCode').attr('value', $("#frmMain_currencyCode").val());
                $('#formRate_sellRate').attr('value', $("#frmMain_sellRate").val());
                $("#tempformRate").click();
				rateParams = {};
                rateParams.currencyCode = "frmMain_currencyCode";
                rateParams.sellRate = "frmMain_sellRate";
                rateParams.onClose = function(currencyCode,sellRate) {
					if (sellRate == '') {
						alert("This currency not have rate");
					}else{
						$("#frmMain_currencyCode").attr("value",currencyCode);
						$("#frmMain_sellRate").attr("value",sellRate);
						$("#frmMain_destAmount").val('');
						$("#btnSave").hide();
						$("#btnGeneratePDF").hide();
            }
				}
			}
            
            function amountDestination() {
                var usdequiv = $("#frmMain_amtTxnTcy").val();
                var rate     = $("#frmMain_sellRate").val();
                var amount   = usdequiv*rate;
                
				if (rate == ''){
					alert("Rate can’t be empty for calculation.")
				}else{
                $("#frmMain_destAmount").val(amount);
					$("#btnSave").show();
					$("#btnGeneratePDF").hide();
				}
            }
            
            function getRefNum(){
                $('#formRef_refNetworkNo').attr('value',$('#frmMain_refNetworkNo').val());
                $('#formRef_codAccNo').attr('value',$('#frmMain_codAccNo').val());
                $('#formRef_nameCurrency').attr('value',$('#frmMain_nameCurrency').val());
                $('#formRef_amtTxnAcy').attr('value',$('#frmMain_amtTxnAcy').val());
                $('#formRef_amtTxnTcy').attr('value',$('#frmMain_amtTxnTcy').val());
                $('#formRef_currencyCode').attr('value',$('#frmMain_currencyCode').val());
                $('#formRef_sellRate').attr('value',$('#frmMain_sellRate').val());
                $('#formRef_destAmount').attr('value',$('#frmMain_destAmount').val());
                $('#tempformRef').click();{                  
					$("#btnSave").hide();
                }
            }
            
            function saveVal(){
                $('#formSave_refNetworkNo').attr('value',$('#frmMain_refNetworkNo').val());
                $('#formSave_codAccNo').attr('value',$('#frmMain_codAccNo').val());
                $('#formSave_nameCurrency').attr('value',$('#frmMain_nameCurrency').val());
                $('#formSave_amtTxnAcy').attr('value',$('#frmMain_amtTxnAcy').val());
                $('#formSave_amtTxnTcy').attr('value',$('#frmMain_amtTxnTcy').val());
                $('#formSave_currencyCode').attr('value',$('#frmMain_currencyCode').val());
                $('#formSave_destAmount').attr('value',$('#frmMain_destAmount').val());
				$('#formSave_sellRate').attr('value',$('#frmMain_sellRate').val());
                $('#tempformSave').click();{                  
                }
            }
                
            //
            $("#btnLookupDestCcyInq").click(function() {
                if (!(
                ($(this).button('option').disabled != undefined) &&
                    ($(this).button('option', 'disabled'))
            )) {
                    dlgParams = {};
                    dlgParams.id = 'frmMain_currencyCode'; //'frmMain_currencyCode'
                    dlgParams.desc = 'frmMain_FlagCode'; //frmMain_FlagCode
                    dlgParams.onClose = function(){
						var cur = $.trim($("#frmMain_currencyCode").val());
						if (cur == ''){
							alert("Please select currency");
							$("#frmMain_currencyCode").focus();
							return;
						}else{
                        rateDes();
						}
                    }
					
                    var $tmp = $("#ph-dlg").dialog("option", "title");
                    $("#ph-dlg").dialog("option", "position", "center");
                    $("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
                    $("#ph-dlg").dialog("option", "height", 450);
					
                    $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function(){
                        $("#frmDlgCCodeRateInq_term").attr("value", $('#frmMain_currencyCode').val());
                        $("#aDlgCCodeRateInq").click();
                    })
                    .unbind("dialogclose")
                    .bind("dialogclose", function(){
                        $(this).dialog({title: $tmp});
                    })
                    .dialog({
                        title: '<s:text name="label.mcr.destination.currencyname" />'
                    })
                    .dialog("open");
                }
            });
            
            $("#frmMain_currencyCode").unbind('keydown');
            $("#frmMain_currencyCode").bind('keydown', function(e) {
                if(e.keyCode == 9) {
                    rateDes();
					$("#btnCalc").show();
                }
            });
                
            $("#btnSearchInq").click(function(){
				var noRep = $.trim($('#frmMain_refNetworkNo').val());
				if (noRep == ''){
						alert("Please input the Transaction Ref No");
						$('#frmMain_refNetworkNo').focus();
						return;
				}else{
                getRefNum();
				}            
            });
            
            $("#btnCalc").click(function(){
                 amountDestination();
                   
            });
            
        });        
    </script>
</s:if>