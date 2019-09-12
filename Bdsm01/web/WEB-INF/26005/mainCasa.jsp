<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <s:url var="ajaxUpdateTitle" action="26302_title" />
    <s:url var="ajaxUpdateButtons" action="26005_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />
    <script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
    <script type="text/javascript" src="_js/jquery.maskedinput-1.2.2.js"></script>
    <s:form id="frmCasa" action="26302_execute">
        <s:token name="frmCasaToken"/>
    </s:form>
    <sj:a id="tempCasa" formIds="frmCasa" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
    <%-- Authentication --%>
    <s:form id="frmDlgAuth" action="dlg">
        <s:hidden name="dialog" value="dlgAuth" />
        <s:hidden name="idMenu" value="26005" />
    </s:form>
    <sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Confirmation --%>
    <s:form id="frmDlgConfirmation" action="dlg">
        <s:hidden name="dialog" value="dlgConfirmation" />
        <s:hidden name="actionConf" />
        <s:token name="confirmation" />
    </s:form>
    <sj:a id="afrmDlgConfirmation" formIds="frmDlgConfirmation" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <%-- Lookup ID Type --%>
    <s:form id="frmDlgIdType" action="dlg">
        <s:hidden name="dialog" value="dlgJenisKartu" />
        <s:hidden name="master" value="idType" />
        <s:token />
    </s:form>

    <sj:a id="aDlgIDType" formIds="frmDlgIdType" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%--  Mailing City --%>
    <s:form id="frmDlgMaiilingTownCity" action="dlg">
        <s:hidden name="dialog" value="dlgMailingTownCity" />
        <s:hidden name="master" value="city" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgMaiilingTownCity" formIds="frmDlgMaiilingTownCity" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Mailling State --%>
    <s:form id="frmDlgMailingState" action="dlg">
        <s:hidden name="dialog" value="dlgMailingState" />
        <s:hidden name="master" value="state" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>

    <sj:a id="aDlgMailingState" formIds="frmDlgMailingState" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Permanent City  --%>
    <s:form id="frmDlgPermaTownCity" action="dlg">
        <s:hidden name="dialog" value="dlgPermaTownCity" />
        <s:hidden name="master" value="city" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgPermaTownCity" formIds="frmDlgPermaTownCity" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <%-- Permanent State  --%>
    <s:form id="frmDlgPermaState" action="dlg">
        <s:hidden name="dialog" value="dlgPermaState" />
        <s:hidden name="master" value="state" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgPermaState" formIds="frmDlgPermaState" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- transaction --%>
    <s:form id="frmDlgNilaiTransaksi" action="dlg">
        <s:hidden name="dialog" value="dlgNilaiTransaksi" />
        <s:hidden name="master" value="nilaiTransaksi" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgNilaiTransaksi" formIds="frmDlgNilaiTransaksi" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%--  Profession --%>
    <s:form id="frmDlgProfession" action="dlg">
        <s:hidden name="dialog" value="dlgProfession" />
        <s:hidden name="master" value="occupation" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgProfession" formIds="frmDlgProfession" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Occupation --%>
    <s:form id="frmDlgOccupation" action="dlg">
        <s:hidden name="dialog" value="dlgOccupation" />
        <s:hidden name="master" value="occupation" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgOccupation" formIds="frmDlgOccupation" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Nationality --%>
    <s:form id="frmDlgNationality" action="dlg">
        <s:hidden name="dialog" value="dlgNationality" />
        <s:hidden name="master" value="country" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgNationality" formIds="frmDlgNationality" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Employer City --%>
    <s:form id="frmDlgEmployerCity" action="dlg">
        <s:hidden name="dialog" value="dlgEmployerCity" />
        <s:hidden name="master" value="city" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgEmployerCity" formIds="frmDlgEmployerCity" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Business Type --%>
    <s:form id="frmDlgBusinessType" action="dlg">
        <s:hidden name="dialog" value="dlgBusinessType" />
        <s:hidden name="master" value="bidangUsaha" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgBusinessType" formIds="frmDlgBusinessType" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Religion --%>
    <s:form id="frmDlgReligion" action="dlg">
        <s:hidden name="dialog" value="dlgReligion" />
        <s:hidden name="master" value="religion" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgReligion" formIds="frmDlgReligion" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Education --%>
    <s:form id="frmDlgCustEducation" action="dlg">
        <s:hidden name="dialog" value="dlgCustEducation" />
        <s:hidden name="master" value="custEducation" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgCustEducation" formIds="frmDlgCustEducation" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Salutation --%>
    <s:form id="frmDlgSalutation" action="dlg">
        <s:hidden name="dialog" value="dlgSalutation" />
        <s:hidden name="master" value="salutation" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgSalutation" formIds="frmDlgSalutation" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Persetujuan Data  --%>
    <s:form id="frmDlgPersetujuanData" action="dlg">
        <s:hidden name="dialog" value="dlgPersetujuanData" />
        <s:hidden name="master" value="persetujuanData" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgPersetujuanData" formIds="frmDlgPersetujuanData" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Komunikasi  --%>
    <s:form id="frmDlgCommunication" action="dlg">
        <s:hidden name="dialog" value="dlgCommunication" />
        <s:hidden name="master" value="communication" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgCommunication" formIds="frmDlgCommunication" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Status Indicator  --%>
    <s:form id="frmDlgStatIndikator" action="dlg">
        <s:hidden name="dialog" value="dlgStatIndikator" />
        <s:hidden name="master" value="statIndikator" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgStatIndikator" formIds="frmDlgStatIndikator" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Sumber Dana --%>
    <s:form id="frmDlgSumberDana" action="dlg">
        <s:hidden name="dialog" value="dlgSumberDana" />
        <s:hidden name="master" value="sumberDana" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgSumberDana" formIds="frmDlgSumberDana" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Sumber Dana Lain --%>
    <s:form id="frmDlgDanaLain" action="dlg">
        <s:hidden name="dialog" value="dlgDanaLain" />
        <s:hidden name="master" value="sumberDana" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgDanaLain" formIds="frmDlgDanaLain" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Pendapatan --%>
    <s:form id="frmDlgPendapatan" action="dlg">
        <s:hidden name="dialog" value="dlgIncPerMonthType" />
        <s:hidden name="master" value="incomePerMonthType" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgPendapatan" formIds="frmDlgPendapatan" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%--  Marital Status--%>
    <s:form id="frmDlgMaritalStatus" action="dlg">
        <s:hidden name="dialog" value="dlgMarStat" />
        <s:hidden name="master" value="marStat" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgMaritalStatus" formIds="frmDlgMaritalStatus" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%--  AO Business--%>
    <s:form id="frmDlgAoBusiness" action="dlg">
        <s:hidden name="dialog" value="dlgAoBusiness" />
        <s:hidden name="master" value="ao" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgAoBusiness" formIds="frmDlgAoBusiness" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%--  AO operation--%>
    <s:form id="frmDlgAoOperation" action="dlg">
        <s:hidden name="dialog" value="dlgAoOperation" />
        <s:hidden name="master" value="ao" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgAoOperation" formIds="frmDlgAoOperation" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%--  Tujuan Pembukaan--%>
    <s:form id="frmDlgTujuanPembukaan" action="dlg">
        <s:hidden name="dialog" value="dlgTujuanPembukaan" />
        <s:hidden name="master" value="tujuanPembukaan" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgTujuanPembukaan" formIds="frmDlgTujuanPembukaan" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- LOB  --%>
    <s:form id="frmDlgLob" action="dlg">
        <s:hidden name="dialog" value="dlgLob" />
        <s:hidden name="master" value="lob" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgLob" formIds="frmDlgLob" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Branch Account --%>
    <s:form id="frmDlgAcctBranch" action="dlg">
        <s:hidden name="dialog" value="dlgAcctBranch" />
        <s:hidden name="master" value="acctBranch" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgAcctBranch" formIds="frmDlgAcctBranch" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Product Code --%>
    <s:form id="frmDlgProduct" action="dlg">
        <s:hidden name="dialog" value="dlgProduct" />
        <s:hidden name="master" value="productByCode" />
        <s:hidden name="term" value=""/>
        <s:token />
    </s:form>
    <sj:a id="aDlgProduct" formIds="frmDlgProduct" targets="ph-dlg" cssClass="ui-helper-hidden" />
    <%-- Home Status --%>
    <s:form id="frmDlgHomeStatus" action="dlg">
        <s:hidden name="dialog" value="dlgHomeStatus" />
        <s:hidden name="master" value="homeStatus" />
        <s:hidden name="term" value="" />

        <s:token />
    </s:form>
    <sj:a id="aDlgHomeStatus" formIds="frmDlgHomeStatus" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <s:form id="frmMain" name="frmMain" action="26005" theme="css_xhtml" validate="true" method="post">        
        <script type="text/javascript">
            <%@include file="formValidation.js" %>
        </script>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Data Nasabah</legend>
            <table class="wwFormTable">
                <tbody>
                    <tr>
                        <s:textfield 
                            name="so.applicationID"
                            size="5"
                            cssClass="ui-widget ui-widget-content"
                            hidden="true"
                            />
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.kodecab" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="so.branchCode"
                                size="5"
                                cssClass="ui-widget ui-widget-content"
                                value="%{#session.cdBranch}"
                                />
                            <s:textfield 
                                name="so.branchCodeName"
                                size="20"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr> 
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.ktp" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.nik"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                readonly="false"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.kartuiden" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.idcardType"/>
                            <s:textfield
                                name="so.idcardTDesc"
                                cssClass="ui-widget ui-widget-content"
                                readonly="false"
                                size="30"
                                />

                        </td>
                    </tr>
                    <tr>  
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.salutation" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name ="so.salutation" />
                            <s:textfield 
                                name="so.salutationDesc"
                                cssClass="ui-widget ui-widget-content"
                                size="20"
                                />

                    </tr>
                    <tr>

                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.nama" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td>
                            <s:textfield 
                                name="so.fullName"
                                size="60"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                readonly="false"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.alias" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.aliasName"
                                size="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                        <td class="tdLabel" hidden="true"style="font-style:italic"><s:text name="label.nasabah.shrtName" /></td>
                        <td class="tdLabel" hidden="true" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">              
                            <s:textfield 
                                hidden="true"
                                name="so.shortName"
                                size="40"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                readonly="false"
                                />
                        </td>
                    </tr>
                    <tr> 
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.PermaAddress1"  /></td>
                        <td class="tdLabel" style="font-style:italic" ><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.permaAddress1"
                                size="60"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span></td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.permaAddress2"
                                size="60"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr> 

                        <td class="tdLabel" style="font-style:italic" ></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span></td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.permaAddress3"
                                size="60"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.kota" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:hidden name="so.permaTownCity" />
                            <s:hidden name="so.permaTownCityKTP" />
                            <s:textfield 
                                name="so.permaTownCityDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                readonly="false"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic" > <s:text name="label.nasabah.kodepos" /></td>
                        <td class="tdLabel" style="font-style:italic"> <span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.permaZipCode"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>

                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.provinsi" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:hidden name="so.permaState" />
                            <s:hidden name="so.permaStateKTP" />
                            <s:textfield 
                                name="so.permaStateDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr> 
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.alamatterkini"  /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.mailAddrs1"
                                size="60"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                />
                        </td> 
                    </tr>
                    <tr>

                        <td class="tdLabel" style="font-style:italic"></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span></td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.mailAddrs2"
                                size="60"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr> 

                        <td class="tdLabel" style="font-style:italic"></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span></td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.mailAddrs3"
                                size="60"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.kota" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.maillingTownCity" />
                            <s:hidden name="so.maillingTownCityKTP" />
                            <s:textfield 
                                name="so.maillingTownCityDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.kodepos" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.maillingCode"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.provinsi" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.maillingState" />
                            <s:hidden name="so.maillingStateKTP" />
                            <s:textfield 
                                name="so.maillingStateDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />

                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.tgllahir" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:if test="%{so.flagCIF == true}" >
                                <s:textfield 
                                    name="so.birthDate"
                                    size="30"
                                    cssClass="ui-widget ui-widget-content"
                                    readonly="true"
                                    />
                            </s:if>
                            <s:elseif test="%{so.flagCIF == false}">
                                <sj:datepicker
                                    name="so.birthDate"
                                    displayFormat="dd/mm/yy"
                                    firstDay="1"
                                    maxDate="+0d"
                                    minDate="-100y"
                                    yearRange="-120:-10"
                                    buttonImageOnly="true"
                                    showOn="both"
                                    requiredLabel="true" 
                                    cssClass="ui-widget ui-widget-content" 
                                    changeMonth="true" 
                                    changeYear="true"
                                    numberOfMonths="1"
                                    />
                            </s:elseif>
                        </td> 
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.tempatlahir" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.birthPlace"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.telprumah"  /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.residencePhoneNoContry"
                                size="3" 
                                maxLength="3"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:textfield 
                                name="so.residencePhoneNoArea"
                                size="4"
                                maxLength="4"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:textfield 
                                name="so.residencePhoneNo"
                                size="12"
                                maxLength="10"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>  
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.pend" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.lastEdu" />
                            <s:hidden name="so.lastEduKTP" />
                            <s:textfield 
                                name="so.lastEduDesc"  
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.hp" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.mobilePhone"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.email" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.emailAddress"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.namaibu" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.namMother"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr id="highRisknationalityCode">
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.wni" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.nationality" />
                            <s:hidden name="so.highRisknationality" />
                            <s:hidden name="so.highRisknationalityCode" />
                            <s:hidden name="highRisknationalityFlag" />
                            <s:hidden name="highRisknationalityType" />
                            <s:textfield 
                                name="so.nationalityDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.jeniskelamin" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.gender" />
                            <s:hidden name="so.genderDesc" />
                            <sj:checkboxlist
                                id="gender"
                                name="gender"
                                cssClass="ui-widget ui-widget-content"
                                list="{'Male', 'Female'}"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.agama" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:hidden name="so.religion" />
                            <s:hidden name="so.religionKTP" />

                            <s:textfield 
                                name="so.religionDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.statusperkawinan" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.marStat" />
                            <s:hidden name="so.marStatKTP" />

                            <s:textfield 
                                name="so.marStatDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.badanas" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <sj:checkboxlist 
                                id="badanAS"
                                name="badanAS"
                                cssClass="ui-widget ui-widget-content"
                                list="{'Yes', 'No'}"
                                />
                            <s:hidden name="so.badanAS" />
                            <s:hidden name="so.badanASDesc" />
                        </td>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.greencard" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <sj:checkboxlist 
                                id="greenCard"
                                name="greenCard"
                                cssClass="ui-widget ui-widget-content"
                                list="{'Yes', 'No'}"
                                />
                            <s:hidden name="so.greenCard" />
                            <s:hidden name="so.greenCardDesc" />
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.alamatas" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <sj:checkboxlist 
                                id="alamatAS"
                                name="alamatAS"
                                cssClass="ui-widget ui-widget-content"
                                list="{'Yes', 'No'}"
                                />
                            <s:hidden name="so.alamatAS" />
                            <s:hidden name="so.alamatASDesc"/>
                        </td>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.fatca" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <sj:checkboxlist 
                                id="fatca"
                                name="fatca"
                                cssClass="ui-widget ui-widget-content"
                                list="{'Yes','NA', 'No'}"
                                />
                            <s:hidden name="so.fatca" />
                            <s:hidden name="so.fatcaDesc" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.tin" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.tin"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.npwp" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.incomeTaxNo"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.stattinggal" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:hidden name="so.homeStatus" />
                            <s:textfield 
                                name="so.homeStatusDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.aobusiness" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.aoBusiness"
                                size="5"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:textfield 
                                name="so.aoBusinessDesc"
                                size="20"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.aooperation" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield  
                                name="so.aoOperation"
                                size="5"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:textfield 
                                name="so.aoOperationDesc"
                                size="20"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.persetujuandata" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.dataTransXtractFlagDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:hidden name="so.dataTransXtractFlag" />
                        </td>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.nasabah.penawarandata" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px" >
                            <s:hidden name="so.communication" />
                            <s:textfield 
                                name="so.communicationDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />

                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Data Suami /Istri atau Orang tua </legend>
            <table class="wwFormTable">             
                <tbody>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.datatambahan.nama" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.spouseName"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.datatambahan.pekerjaan" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:hidden name ="so.occupation"/>
                            <s:textfield
                                name="so.occupationDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>

                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.datatambahan.kantor" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.spouseEmployer"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic" ><s:text name="label.datatambahan.jabatan" /></td>
                        <td class="tdLabel" style="font-style:italic" ><span style="color:red"></span>:</td>
                        <td style="padding-right:5px" >
                            <s:textfield 
                                name="so.spouseJobTitle"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Data Pekerjaan / Lainnya</legend>
            <table class="wwFormTable">
                <tbody>
                    <tr id="highRiskbusinessCode">
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.bidang" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.businessType" />
                            <s:hidden name="so.highRiskbusiness" />
                            <s:hidden name="so.highRiskbusinessCode" />
                            <s:hidden name="highRiskbusinessFlag" />
                            <s:hidden name="highRiskbusinessType" />
                            <s:textfield 
                                name="so.businessTypeDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.tempat" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.employerName"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"

                                />
                        </td>
                    </tr>
                    <tr id="highRiskprofessionCode">
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.datatambahan.pekerjaan" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.professionCode" />
                            <s:hidden name="so.highRiskprofession" />
                            <s:hidden name="so.highRiskprofessionCode" />                                                        							
                            <s:hidden name="so.profession" />
                            <s:hidden name="so.professionKTP" />
                            <s:hidden name="highRiskprofessionFlag" />
                            <s:hidden name="highRiskprofessionTypePEP" />
                            <s:hidden name="highRiskprofessionType" />
                            <s:textfield 
                                name="so.professionDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>  
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.pangkat"  /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.jobTitle"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                readonly="false"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.alamat" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.employerAddress1"
                                size="55"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span></td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.employerAddress2"
                                size="55"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span></td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.employerAddress3"
                                size="55"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>

                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.kota" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.employerTownCity" />
                            <s:textfield 
                                name="so.employerTownCityDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />

                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.kodepos" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.employerZipCode"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.notelp" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.employerPhoneNoCountry"
                                size="3"
                                maxLength="3"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:textfield 
                                name="so.employerPhoneNoArea"
                                size="4"
                                maxLength="4"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:textfield 
                                name="so.employerPhoneNo"
                                size="12"
                                maxLength="10"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:textfield 
                                name="so.employerPhoneNoExt"
                                size="5"
                                maxLength="4"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.pendapatan" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.monthLyIncome" />
                            <s:textfield 
                                name="so.monthLyIncomeDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />


                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pekerjaan.nilaitransaksi" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.expectedLimit" />
                            <s:textfield 
                                name="so.expectedLimitDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />


                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Data Pembukaan Rekening</legend>
            <table class="wwFormTable">
                <tbody>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.nasabah.acctbranch" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.branchCodeCasa"
                                size="5"
                                cssClass="ui-widget ui-widget-content"
                                value="%{#session.cdBranch}"
                                />
                            <s:textfield 
                                name="so.branchCodeCasaDesc"
                                size="20"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:hidden name="brnBranch"/>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.productname" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.productCode"
                                cssClass="ui-widget ui-widget-content"
                                size="5"
                                />
                            <s:hidden name="brnProd" />
                            <s:textfield 
                                name="so.productCodeDesc"
                                cssClass="ui-widget ui-widget-content"
                                size="50"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.actitle" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.acctTitle"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.lobcode" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.lob"/>
                            <s:textfield 
                                name="so.lobDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                readonly="false"
                                />

                            <s:hidden name="brnLOB" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.relation" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.relation"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.indicator" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.stateIndicator" />
                            <s:textfield 
                                name="so.stateIndicatorDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                value="MAIL"
                                />

                        </td> 
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.echannel" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.echannel" />
                            <s:hidden name="so.echannelDesc"/>
                            <sj:checkboxlist 
                                id="echannel"
                                name="echannel"
                                cssClass="ui-widget ui-widget-content"
                                list="{'Yes', 'No'}"
                                />
                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.tujuan" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.tujuanPembukaan" />
                            <s:textfield 
                                name="so.tujuanPembukaanDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />

                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.sumberdana" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right:5px">
                            <s:hidden name="so.sumberDana" />
                            <s:textfield 
                                name="so.sumberDanaDesc"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                />

                        </td>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.pembukaan.sumberdanalain" /></td>
                        <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                        <td style="padding-right:5px">
                            <s:textfield 
                                name="so.sumberDanaLain"
                                size="30"
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
            <table>
                <tbody>
                    <tr>
                        <td>
                            <sj:submit 
                                id="btnCreate" 
                                formIds="frmMain" 
                                buttonIcon="ui-icon-gear"
                                button="true" 
                                key="button.create.casa" 
                                disabled="false" 
                                targets="ph-main"
                                onBeforeTopics="beforeSubmitCasa" 
                                />
                        </td>
                        <td>
                            <sj:submit 
                                id="btnCancelCasa" 
                                buttonIcon="ui-icon-gear"
                                button="true" 
                                key="button.cancel.casa" 
                                disabled="false" 
                                targets="ph-main"
                                onBeforeTopics="beforeCasa" 
                                />   
                        </td>
                    </tr>

                    <s:hidden name="state" />
                    <s:hidden name="inputNik" />
                    <s:hidden name="flagButton" /> 
                </tbody>
            </table>
        </fieldset>         
        <div id="divMessage" />
        <div id="divInnerMessage" />
        <div id="divwaitingMessage" />
        <div id="divwaitingRespond" />
        <s:hidden name="current" />
        <s:hidden name="verified" />
        <s:hidden name="cancelling" />
        <s:token name="frmCasaInputToken"/>
        <%-- Hidden Object Field --%>
        <s:hidden name="sessions" value="%{#session.ACTION}" />
        <s:hidden name="so.custFirstName" />
        <s:hidden name="so.custMidName" />
        <s:hidden name="so.custLastName" />
        <s:hidden name="so.maillingCountry" />
        <s:hidden name="so.maillingCountryDesc" />
        <s:hidden name="so.permaCountry" />
        <s:hidden name="so.permaCountryDesc" /> 
        <s:hidden name="so.countryOfResidenceDesc" />
        <s:hidden name="so.countryOfResidence" />
        <s:hidden name="so.holdAddress1" />
        <s:hidden name="so.holdAddress2" />
        <s:hidden name="so.holdAddress3" />
        <s:hidden name="so.holdZipCode" />
        <s:hidden name="so.holdTownCity" />
        <s:hidden name="so.holdState" />
        <s:hidden name="so.holdCountry" />
        <s:hidden name="so.holdCountryDesc" />
        <s:hidden name="so.flagCIF" />
        <s:hidden name="so.cifNumber" />
        <s:hidden name="so.custId" />
        <s:hidden name="so.acctNo" />
        <s:hidden name="so.flgProcess" />
        <s:hidden name="so.cifType" />
        <s:hidden name="so.category" />
        <s:hidden name="so.flgIctype" />
        <s:hidden name="so.staff" />
        <s:hidden name="so.sandiDatiII" />
        <s:hidden name="so.menuAccount" />
        <s:hidden name="listFieldTag" />
        <s:hidden name="lastStatus" id="lastStatusParse"/>
        <s:token name="frmCasaInputToken"/>
        <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
        <s:hidden name="idMaintainedSpv" />
        <s:hidden name="so.codUserID" value="%{#session.idUser}" />
    </s:form>
    <%-- TabbedPanel for Grid --%>
    <sj:a id="btnLookupJnsKartuIden" button="true" >...</sj:a>
    <sj:a id="btnLookupMailingTownCity" button="true" >...</sj:a>
    <sj:a id="btnLookupMailingState" button="true" >...</sj:a>
    <sj:a id="btnLookupPermaTownCity" button="true" >...</sj:a>
    <sj:a id="btnLookupPermaState" button="true" >...</sj:a>
    <sj:a id="btnLookupNationality" button="true" >...</sj:a>
    <sj:a id="btnLookupSalutation" button="true" >...</sj:a>
    <sj:a id="btnLookupProfession" button="true" >...</sj:a>
    <sj:a id="btnLookupBusinessType" button="true" >...</sj:a>
    <sj:a id="btnLookupReligion" button="true" >...</sj:a>
    <sj:a id="btnLookupPersetujuanData" button="true" >...</sj:a>
    <sj:a id="btnLookupStateIndicator" button="true" >...</sj:a>
    <sj:a id="btnLookupTujuan" button="true" >...</sj:a>
    <sj:a id="btnLookupSumberDana" button="true" >...</sj:a>
    <sj:a id="btnLookupPendapatan" button="true" >...</sj:a>
    <sj:a id="btnLookupOfficeCity" button="true" >...</sj:a>
    <sj:a id="btnLookupAoBusiness" button="true" >...</sj:a>
    <sj:a id="btnLookupAoOperation" button="true" >...</sj:a>
    <sj:a id="btnLookupOccupation" button="true" >...</sj:a>
    <sj:a id="btnLookupCustEducation" button="true" >...</sj:a>
    <sj:a id="btnLookupTujuanPembukaan" button="true" >...</sj:a>
    <sj:a id="btnLookupLob" button="true" >...</sj:a>
    <sj:a id="btnLookupBranchCode" button="true" >...</sj:a>
    <sj:a id="btnLookupProduct" button="true" >...</sj:a>
    <sj:a id="btnLookupMaritalStatus" button="true" >...</sj:a>
    <sj:a id="btnLookupHomeStatus" button="true" >...</sj:a>
    <sj:a id="btnLookupNilaiTransaksi" button="true" >...</sj:a>
    <sj:a id="btnLookupCommunication" button="true" >...</sj:a>

        <script type="text/javascript">
            jQuery(document).ready(function() {
                var messaging = "Please Wait while page Loading..";
			
                $("#ph-main").find("div[id^='wwctrl_frmMain_label_']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("tr > td > div").attr("style","line-height:10px"); /* because tag s:label generate double text */
                $("#ph-main").scrollTop(0);
                if (($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
                    $("#rbBS").buttonset("destroy");
                    $("#tempTitle").click();
                    $("#tempButtons").click();
                } else {
                    $("#rbBS").data("rdb").callCurrent();
                    if ($("#actionError").length == 0) {
                        $("#rbBS").data("rdb").clear(false);
                    }
                }
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelReligion']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelLastEdu']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelpermaState']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelPermatown']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelmarStat']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelmaillingTownCity']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelmaillingState']").remove(); /* because tag s:label generate double text */
                $("#ph-main").find("div[id^='wwlbl_frmMain_labelprofession']").remove(); /* because tag s:label generate double text */
			
                $("#frmMain_so_applicationID").hide();
                $("#frmMain_dummy").hide();
                getRadioChosen();
                var flagging  = $("#frmMain_so_flagCIF").val();
                //alert(flagging);
			
				
                //alert("SESSION :" + $("#frmMain_sessions").val());
                var sessionFlag = $("#frmMain_sessions").val();
                //alert("SESSION VALUE :" + sessionFlag);
                if(sessionFlag == "PROCESSED"){
                    waitingMessage(3,messaging,"divwaitingMessage");
                    CIFreadonly(flagging);
                    //alert("NEED CLOSE EXISTING");
                    //$("#divwaitingMessage")addClass("ui-helper-hidden");
                } else if(sessionFlag == "NEW"){
                    waitingMessage(3,messaging,"divwaitingMessage");
                    ektPRplication();
                    CIFreadonly(flagging);
                    //alert("NEED CLOSE");
                    //$("#divwaitingMessage").dialog("close");
                } else if(sessionFlag == "CREATED"){
                    $("#divwaitingMessage").dialog("close");
                    disableSearch();
                    enableDisableType(3);
                    disableValueall();
                    if(flagging == 'true'){
                        messageBoxClass(3, '<s:text name="26301.success.CIF.message.exist" ><s:param><s:text name="%{so.cifNumber}" /></s:param></s:text><br><s:text name="26301.success.Casa.message.new" ><s:param><s:text name="%{so.acctNo}" /></s:param></s:text>'+' <br>Press "OK" to Create new Account' ,function(){
                            $("#frmGo_idMenu").val("26301");
                            $("#frmGo_goAction").val("exec");
                            $("#btnGo").click();
                        });
                    } else {
                        messageBoxClass(3, '<s:text name="26301.success.CIF.message.new" ><s:param><s:text name="%{so.cifNumber}" /></s:param></s:text><br><s:text name="26301.success.Casa.message.new" ><s:param><s:text name="%{so.acctNo}" /></s:param></s:text>'+' <br>Press "OK" to Create new Account' ,function(){
                            $("#frmGo_idMenu").val("26301");
                            $("#frmGo_goAction").val("exec");
                            $("#btnGo").click();
                        });
                    }
                    
                    $("#btnCreate").attr("disabled","true");
                } else if(sessionFlag == "FAILED"){
                    disableSearch();
                    enableDisableType(3);
                    disableValueall();
                    messageBoxClass(3, '<s:text name="26301.failed.CIF" ></s:text>'+' \n Press "OK" to Create new Account' ,function(){
                        $("#frmGo_idMenu").val("26301");
                        $("#frmGo_goAction").val("exec");
                        $("#btnGo").click();
                    });
                }
                //
                $("#frmMain_so_custId").val($("#frmMain_so_cifNumber").val());
                $("#frmMain_so_flgProcess").val("N");
                hiddenWrapper("echannel");
                hiddenWrapper("gender");
                hiddenWrapper("salutation");
                hiddenWrapper("idcardType");
                hiddenWrapper("mailingTownCity");
                hiddenWrapper("maillingState");
                hiddenWrapper("permaTownCity");
                hiddenWrapper("permaState");
                hiddenWrapper("religion");
                hiddenWrapper("nationality");
                hiddenWrapper("marStat");
                hiddenWrapper("lastEdu");
                //hiddenWrapper("aoBusiness");
                hiddenWrapper("businessType");
                hiddenWrapper("profession");
                hiddenWrapper("employerTownCity");
                hiddenWrapper("monthLyIncome");
                hiddenWrapper("expectedLimit");
                hiddenWrapper("tujuanPembukaan");
                hiddenWrapper("sumberDana");
                $('#frmMain_so_birthDate').mask("99/99/9999"); 
                $("#divwaitingMessage").dialog("close");
                $("#divwaitingRespond").dialog("close");
			
                $("#divKtp26301").dialog("close");
                $("#divKtp26301").remove();
			
                $("#divMain26301").dialog("close");
                $("#divMain26301").remove();
			
			
                // Inserting data from backend Query
            });
            function checkHighRisk(fieldName, realName){
                var fieldToCheck = $("#frmMain_"+fieldName).val();
                //alert("|" +fieldToCheck+ "|");
                if(fieldToCheck != "" && fieldToCheck != null && fieldToCheck != ''){
                    // highRisk
                    //alert(fieldToCheck);
                    var generalHR = realName;
                    if(fieldToCheck.substr(0,3)=="PEP") {
                        $("#frmMain_"+generalHR+"Flag").val("PEP");	
                        $("#frmMain_"+generalHR+"TypePEP").val("Politically Exposed Person");
                    } else {
                        $("#frmMain_"+generalHR+"Flag").val("HR");
                        if("highRiskprofession" == generalHR){
                            $("#frmMain_"+generalHR+"Type").val('High Risk Profession');	
                        } else if("highRiskbusiness" == generalHR){
                            $("#frmMain_"+generalHR+"Type").val('High Risk Business');	
                        } else if("highRisknationality" == generalHR){
                            $("#frmMain_"+generalHR+"Type").val('High Risk Country');	
                        } else {
                            $("#frmMain_"+generalHR+"Type").val(null);
                        }
                    }
                }
            }
            function hiddenWrapper(element){
                $("#frmMain_so_"+element).wrap("<div id='wwgrp_frmMain_so_'"+element+" class='wwgrp' ><div id='wwctrl_frmMain_so_gender'"+element+" class='wwwctrl' ></div></div>");
            }
            function ektPRplication(){
                var arrayEktp = "permaTownCity,permaState,maillingTownCity,maillingState,lastEdu,marStat,profession";
                var result = arrayEktp.split(",");
                for(var i=0;i<result.length;i++){
                    //alert("TEST :" + result[i]);
                    var valKtp = $("#frmMain_so_"+result[i]).val();
                    if(valKtp == 'EKTP'){
                        var elemName = "frmMain_so_"+result[i];
                        //alert(elemName);
                        var trueElement = document.getElementById(elemName);  
                        //alert('#'+elemName);	
                        //alert("2 :" +trueElement);
                        $('#wwctrl_'+elemName+'Desc').before('<div id="error_'+result[i]+'"><span class="errorMessage" classname="errorMessage">Mapping Not Found for :'+$("#"+elemName+"KTP").val() + '</span></div>');
                    }
                }
            }
            function removeValidation(rowid) {
                var row =  document.getElementById(rowid);
                //highRiskprofessionCode
                //alert("3 :" +rowid);
                //alert(row);
                if(row != null){
                    row.parentNode.removeChild(row);
                }
                //alert("LAST");
			
            }
            function enableDisableType(flagging) {
                if(flagging=='1'){
                    $("#gender").buttonset("enable");
                    $("#gender-1").button("enable");
                    $("#gender-2").button("enable");
                    $("#fatca").buttonset("enable");
                    $("#fatca-1").button("enable");
                    $("#fatca-2").button("enable");
                    $("#fatca-3").button("enable");
                    $("#echannel").buttonset("enable");
                    $("#echannel-1").button("enable");
                    $("#echannel-2").button("enable");                
                    $("#alamatAS").buttonset("enable");
                    $("#alamatAS-1").button("enable");
                    $("#alamatAS-2").button("enable");
                    $("#greenCard").buttonset("enable");
                    $("#greenCard-1").button("enable");
                    $("#greenCard-2").button("enable");
                    $("#badanAS").buttonset("enable");
                    $("#badanAS-1").button("enable");
                    $("#badanAS-2").button("enable");
                } else if(flagging=='2') {
                    $("#gender").buttonset("disable");
                    $("#gender-1").button("disable");
                    $("#gender-2").button("disable");
                    $("#fatca").buttonset("disable");
                    $("#fatca-1").button("disable");
                    $("#fatca-2").button("disable");
                    $("#fatca-3").button("disable");
                    $("#echannel").buttonset("enable");
                    $("#echannel-1").button("enable");
                    $("#echannel-2").button("enable");                
                    $("#alamatAS").buttonset("disable");
                    $("#alamatAS-1").button("disable");
                    $("#alamatAS-2").button("disable");
                    $("#greenCard").buttonset("disable");
                    $("#greenCard-1").button("disable");
                    $("#greenCard-2").button("disable");
                    $("#badanAS").buttonset("disable");
                    $("#badanAS-1").button("disable");
                    $("#badanAS-2").button("disable");
                } else {
                    $("#gender").buttonset("disable");
                    $("#gender-1").button("disable");
                    $("#gender-2").button("disable");
                    $("#fatca").buttonset("disable");
                    $("#fatca-1").button("disable");
                    $("#fatca-2").button("disable");
                    $("#fatca-3").button("disable");
                    $("#echannel").buttonset("disable");
                    $("#echannel-1").button("disable");
                    $("#echannel-2").button("disable");                
                    $("#alamatAS").buttonset("disable");
                    $("#alamatAS-1").button("disable");
                    $("#alamatAS-2").button("disable");
                    $("#greenCard").buttonset("disable");
                    $("#greenCard-1").button("disable");
                    $("#greenCard-2").button("disable");
                    $("#badanAS").buttonset("disable");
                    $("#badanAS-1").button("disable");
                    $("#badanAS-2").button("disable");
                }
            
            }
            function getRadioChosen() {
                //alert("gender_des :" + $("#frmMain_so_gender").val());
                if($("#frmMain_so_gender").val() != '') {		
                    if($("#frmMain_so_gender").val() == 'M'){
                        $("#gender-2").attr("checked", false).button("refresh");
                        $("#gender-1").attr("checked", true).button("refresh"); 
                    } else if($("#frmMain_so_gender").val() == 'F') {
                        $("#gender-1").attr("checked", false).button("refresh"); 
                        $("#gender-2").attr("checked", true).button("refresh"); 
                    }
                }
                if ($("#frmMain_so_fatca").val() != '') {
                    if ($("#frmMain_so_fatca").val() == '1') {
                        $("#fatca-3").attr("checked", false).button("refresh");
                        $("#fatca-2").attr("checked", false).button("refresh");
                        $("#fatca-1").attr("checked", true).button("refresh");
                    } else if ($("#frmMain_so_fatca").val() == '3') {
                        $("#fatca-1").attr("checked", false).button("refresh");
                        $("#fatca-3").attr("checked", false).button("refresh");
                        $("#fatca-2").attr("checked", true).button("refresh");
                    } else if ($("#frmMain_so_fatca").val() == '2') {
                        $("#fatca-1").attr("checked", false).button("refresh");
                        $("#fatca-2").attr("checked", true).button("refresh");
                        $("#fatca-3").attr("checked", true).button("refresh");
                    }
                }
                if ($("#frmMain_so_echannel").val() != '') {
                    if ($("#frmMain_so_echannel").val() == 'Y') {
                        $("#echannel-2").attr("checked", false).button("refresh");
                        $("#echannel-1").attr("checked", true).button("refresh");
                    } else if($("#frmMain_so_echannel").val() == 'N'){
                        $("#echannel-1").attr("checked", false).button("refresh");
                        $("#echannel-2").attr("checked", true).button("refresh");
                    }
                }
                if ($("#frmMain_so_alamatAS").val() != '') {
                    if ($("#frmMain_so_alamatAS").val() == '1') {
                        $("#alamatAS-2").attr("checked", false).button("refresh");
                        $("#alamatAS-1").attr("checked", true).button("refresh");
                    } else if($("#frmMain_so_alamatAS").val() == '2') {
                        $("#alamatAS-1").attr("checked", false).button("refresh");
                        $("#alamatAS-2").attr("checked", true).button("refresh");
                    }
                }
                if ($("#frmMain_so_greenCard").val() != '') {
                    if ($("#frmMain_so_greenCard").val() == '1') {
                        $("#greenCard-2").attr("checked", false).button("refresh");
                        $("#greenCard-1").attr("checked", true).button("refresh");
                    } else if($("#frmMain_so_greenCard").val() == '2'){
                        $("#greenCard-1").attr("checked", false).button("refresh");
                        $("#greenCard-2").attr("checked", true).button("refresh");
                    }
                }

                if ($("#frmMain_so_badanAS").val() != '') {
                    if ($("#frmMain_so_badanAS").val() == '1') {
                        $("#badanAS-2").attr("checked", false).button("refresh");
                        $("#badanAS-1").attr("checked", true).button("refresh");
                    } else if($("#frmMain_so_badanAS").val() == '2')  {
                        $("#badanAS-1").attr("checked", false).button("refresh");
                        $("#badanAS-2").attr("checked", true).button("refresh");
                    }
                }
            }
		
            // dropdown
            $("#frmMain_so_stateIndicatorDesc").parent().append($("#btnLookupStateIndicator").detach());
            $("#frmMain_so_tujuan").parent().append($("#btnLookupTujuan").detach());
            $("#frmMain_so_sumberDanaDesc").parent().append($("#btnLookupSumberDana").detach());
            $("#frmMain_so_tujuanPembukaanDesc").parent().append($("#btnLookupTujuanPembukaan").detach());
            $("#frmMain_so_lobDesc").parent().append($("#btnLookupLob").detach());
		
            $("#frmMain_so_branchCode").parent().append($("#frmMain_so_branchCodeName").detach());
            $("#frmMain_so_branchCodeCasa").parent().append($("#frmMain_so_branchCodeCasaDesc").detach());
            $("#frmMain_so_branchCodeCasaDesc").parent().append($("#btnLookupBranchCode").detach());
            $("#frmMain_so_productCode").parent().append($("#frmMain_so_productCodeDesc").detach());
            $("#frmMain_so_productCodeDesc").parent().append($("#btnLookupProduct").detach());

            $("#frmMain_so_residencePhoneNoContry").parent().append(" - ").append($("#frmMain_so_residencePhoneNoArea").detach());
            $("#frmMain_so_residencePhoneNoArea").parent().append("-").append($("#frmMain_so_residencePhoneNo").detach());
        
            $("#frmMain_so_employerPhoneNoCountry").parent().append("-").append($("#frmMain_so_employerPhoneNoArea").detach());
            $("#frmMain_so_employerPhoneNoArea").parent().append("-").append($("#frmMain_so_employerPhoneNo").detach());
            $("#frmMain_so_employerPhoneNo").parent().append(" EXT :").append($("#frmMain_so_employerPhoneNoExt").detach());
		
            function disableSearch(){
                // $("#btnLookupJnsKartuIden").button('option', 'disabled');
                $("#btnLookupJnsKartuIden").hide();	
                $("#btnLookupMailingTownCity").hide();
                $("#btnLookupMailingState").hide();
                $("#btnLookupPermaTownCity").hide();
                $("#btnLookupPermaState").hide();
                $("#btnLookupNationality").hide();
                $("#btnLookupSalutation").hide();
                $("#btnLookupProfession").hide();
                $("#btnLookupBusinessType").hide();
                $("#btnLookupReligion").hide();
                $("#btnLookupPersetujuanData").hide();
                $("#btnLookupCommunication").hide();
                $("#btnLookupPendapatan").hide();
                $("#btnLookupOfficeCity").hide();
                $("#btnLookupAoBusiness").hide();
                $("#btnLookupAoOperation").hide();
                $("#btnLookupOccupation").hide();
                $("#btnLookupCustEducation").hide();
                $("#btnLookupNilaiTransaksi").hide();
                $("#btnLookupMaritalStatus").hide();
                $("#btnLookupHomeStatus").hide();
            }
            function enableSearch(){
                $("#btnLookupJnsKartuIden").show();	
                $("#btnLookupMailingTownCity").show();
                $("#btnLookupMailingState").show();
                $("#btnLookupPermaTownCity").show();
                $("#btnLookupPermaState").show();
                $("#btnLookupNationality").show();
                $("#btnLookupSalutation").show();
                $("#btnLookupProfession").show();
                $("#btnLookupBusinessType").show();
                $("#btnLookupReligion").show();
                $("#btnLookupPersetujuanData").show();
                $("#btnLookupCommunication").show();
                $("#btnLookupPendapatan").show();
                $("#btnLookupOfficeCity").show();
                $("#btnLookupAoBusiness").show();
                $("#btnLookupAoOperation").show();
                $("#btnLookupOccupation").show();
                $("#btnLookupCustEducation").show();
                $("#btnLookupNilaiTransaksi").show();
                $("#btnLookupMaritalStatus").show();
                $("#btnLookupHomeStatus").show();
            }
            function CIFreadonly(values){
                $("#frmMain_so_nik").attr("readonly","true");
                $("#frmMain_so_branchCode").attr("readonly","true");
                $("#frmMain_so_branchCodeName").attr("readonly","true");
                $("#frmMain_so_aoBusiness").parent().append($("#frmMain_so_aoBusinessDesc").detach());
                $("#frmMain_so_aoOperation").parent().append($("#frmMain_so_aoOperationDesc").detach());
                if(values == 'true') {
                    enableDisableType(2);
                    disableSearch();
                    $("#frmMain_so_idcardTDesc").attr("readonly","true");
                    $("#frmMain_so_salutationDesc").attr("readonly","true");
                    $("#frmMain_so_fullName").attr("readonly","true");
                    $("#frmMain_so_aliasName").attr("readonly","true");
                    $("#frmMain_so_nationalityDesc").attr("readonly","true");
                    $("#frmMain_so_permaAddress1").attr("readonly","true");
                    $("#frmMain_so_permaAddress2").attr("readonly","true");
                    $("#frmMain_so_permaAddress3").attr("readonly","true");
                    $("#frmMain_so_permaTownCity").attr("readonly","true");
                    $("#frmMain_so_permaTownCityDesc").attr("readonly","true");
                    $("#frmMain_so_permaState").attr("readonly","true");
                    $("#frmMain_so_permaZipCode").attr("readonly","true");	
				
                    $("#frmMain_so_professionDesc").attr("readonly","true");				
                    $("#frmMain_so_permaStateDesc").attr("readonly","true");
                    $("#frmMain_so_communicationDesc").attr("readonly","true");
				
                    $("#frmMain_so_branchCodeCasa").attr("readonly","true");
                    $("#frmMain_so_branchCodeCasaDesc").attr("readonly","true");

                    $("#frmMain_so_mailAddrs1").attr("readonly","true");
                    $("#frmMain_so_mailAddrs2").attr("readonly","true");
                    $("#frmMain_so_mailAddrs3").attr("readonly","true");
                    $("#frmMain_so_maillingCode").attr("readonly","true");
                    $("#frmMain_so_maillingTownCityDesc").attr("readonly","true");
                    $("#frmMain_so_maillingStateDesc").attr("readonly","true");
                    $("#frmMain_so_birthDate").attr("readonly","true");
                    $("#frmMain_so_birthPlace").attr("readonly","true");
                    $("#frmMain_so_residencePhoneNo").attr("readonly","true");
                    $("#frmMain_so_residencePhoneNoContry").attr("readonly","true");
                    $("#frmMain_so_residencePhoneNoArea").attr("readonly","true");
                    $("#frmMain_so_mobilePhone").attr("readonly","true");
                    $("#frmMain_so_lastEdu").attr("readonly","true");
                    $("#frmMain_so_lastEduDesc").attr("readonly","true");
                    $("#frmMain_so_emailAddress").attr("readonly","true");
                    $("#frmMain_so_religionDesc").attr("readonly","true");
                    $("#frmMain_so_namMother").attr("readonly","true");
                    $("#frmMain_so_lobDesc").attr("readonly","true");
                    $("#frmMain_so_marStat").attr("readonly","true");
                    $("#frmMain_so_jobTitle").attr("readonly","true");				
                    $("#frmMain_so_marStatDesc").attr("readonly","true");
                    $("#frmMain_so_acctTitle").attr("value",$("#frmMain_so_shortName").val());
                    $("#frmMain_so_greenCard").attr("readonly","true");
                    $("#frmMain_so_alamatAS").attr("readonly","true");
                    $("#frmMain_so_badanAS").attr("readonly","true");
                    $("#frmMain_so_echannel").attr("readonly","true");
                    $("#frmMain_so_fatca").attr("readonly","true");
                    $("#frmMain_so_tin").attr("readonly","true");				
                    $("#frmMain_so_businessTypeDesc").attr("readonly","true");
                    $("#frmMain_so_incomeTaxNo").attr("readonly","true");
                    $("#frmMain_so_relation").attr("readonly","true");
                    $("#frmMain_so_stateIndicatorDesc").attr("readonly","true");
                    $("#frmMain_so_homeStatus").attr("readonly","true");
                    $("#frmMain_so_homeStatusDesc").attr("readonly","true");
                    $("#frmMain_so_aoBusinessDesc").attr("readonly","true");
                    $("#frmMain_so_aoBusiness").attr("readonly","true");
                    $("#frmMain_so_aoOperation").attr("readonly","true");
                    $("#frmMain_so_aoOperationDesc").attr("readonly","true");
                    $("#frmMain_so_dataTransXtractFlagDesc").attr("readonly","true");
                    $("#frmMain_so_dataTransXtractFlag").attr("readonly","true");
                    $("#frmMain_so_spouseName").attr("readonly","true");
                    $("#frmMain_so_occupationDesc").attr("readonly","true");
                    $("#frmMain_so_professionDesc").attr("readonly","true");
                    $("#frmMain_so_spouseEmployer").attr("readonly","true");
                    $("#frmMain_so_spouseJobTitle").attr("readonly","true");
                    $("#frmMain_so_employerName").attr("readonly","true");
                    $("#frmMain_so_employerAddress1").attr("readonly","true");
                    $("#frmMain_so_employerAddress2").attr("readonly","true");
                    $("#frmMain_so_employerAddress3").attr("readonly","true");
                    $("#frmMain_so_employerZipCode").attr("readonly","true");
                    $("#frmMain_so_employerTownCity").attr("readonly","true");
                    $("#frmMain_so_employerTownCityDesc").attr("readonly","true");
                    $("#frmMain_so_employerState").attr("readonly","true");
                    $("#frmMain_so_employerCountry").attr("readonly","true");
                    $("#frmMain_so_employementDetails").attr("readonly","true");
                    $("#frmMain_so_employerPhoneNo").attr("readonly","true");
                    $("#frmMain_so_employerPhoneNoCountry").attr("readonly","true");
                    $("#frmMain_so_employerPhoneNoArea").attr("readonly","true");
                    $("#frmMain_so_employerPhoneNoExt").attr("readonly","true");
                    $("#frmMain_so_employerPhoneNoExt").attr("readonly","true");
                    $("#frmMain_so_monthLyIncomeDesc").attr("readonly","true");
                    $("#frmMain_so_expectedLimitDesc").attr("readonly","true");
                    $("#frmMain_so_productCode").attr("readonly","true");
                    $("#frmMain_so_productCodeDesc").attr("readonly","true");
                    $("#frmMain_so_acctTitle").attr("readonly","true");
                    $("#frmMain_so_sumberDanaLain").attr("readonly","true");

                    checkHighRisk('so_highRisknationality','highRisknationality');
                    checkHighRisk('so_highRiskbusiness','highRiskbusiness');
                    checkHighRisk('so_highRiskprofession','highRiskprofession');
                } else {
                    enableDisableType(1);
                    enableSearch();
                    $("#frmMain_so_marStatDesc").parent().append($("#btnLookupMaritalStatus").detach());
                    $("#frmMain_so_aoBusinessDesc").parent().append($("#btnLookupAoBusiness").detach());
		
                    $("#frmMain_so_aoOperationDesc").parent().append($("#btnLookupAoOperation").detach());
                    $("#frmMain_so_professionDesc").parent().append($("#btnLookupProfession").detach());
                    $("#frmMain_so_maillingTownCityDesc").parent().append($("#btnLookupMailingTownCity").detach());
                    $("#frmMain_so_maillingStateDesc").parent().append($("#btnLookupMailingState").detach());
                    $("#frmMain_so_permaTownCityDesc").parent().append($("#btnLookupPermaTownCity").detach());
                    $("#frmMain_so_permaStateDesc").parent().append($("#btnLookupPermaState").detach());
                    $("#frmMain_so_lastEduDesc").parent().append($("#btnLookupCustEducation").detach());
                    $("#frmMain_so_nationalityDesc").parent().append($("#btnLookupNationality").detach());
                    $("#frmMain_so_dataTransXtractFlagDesc").parent().append($("#btnLookupPersetujuanData").detach());
                    $("#frmMain_so_religionDesc").parent().append($("#btnLookupReligion").detach());
                    $("#frmMain_so_businessTypeDesc").parent().append($("#btnLookupBusinessType").detach());
                    $("#frmMain_so_monthLyIncomeDesc").parent().append($("#btnLookupPendapatan").detach());
                    $("#frmMain_so_idcardTDesc").parent().append($("#btnLookupJnsKartuIden").detach());
                    $("#frmMain_so_salutationDesc").parent().append($("#btnLookupSalutation").detach());
                    //$("#frmMain_so_countryOfResidenceDesc").parent().append($("#btnLookupCountry").detach());
                    //$("#frmMain_so_permaCountryDesc").parent().append($("#btnLookupPermaCountry").detach());
                    //$("#frmMain_so_maillingCountryDesc").parent().append($("#btnLookupMailCountry").detach());
                    $("#frmMain_so_employerTownCityDesc").parent().append($("#btnLookupOfficeCity").detach());
                    $("#frmMain_so_acctTitle").attr("value",$("#frmMain_so_fullName").val());
                    $("#frmMain_so_relation").attr("readonly","true");
                    $("#frmMain_so_occupationDesc").parent().append($("#btnLookupOccupation").detach());
                    $("#frmMain_so_homeStatusDesc").parent().append($("#btnLookupHomeStatus").detach());
                    $("#frmMain_so_expectedLimitDesc").parent().append($("#btnLookupNilaiTransaksi").detach());
                    $("#frmMain_so_communicationDesc").parent().append($("#btnLookupCommunication").detach());
                }
			
            }
            function disableValueall(){
                $("#frmMain_so_dataTransXtractFlagDesc").attr("disabled","true");
				
                $("#frmMain_so_applicationID").attr("disabled","true");
                $("#frmMain_so_cifNumber").attr("disabled","true");
                $("#frmMain_so_nik").attr("disabled","true");
                $("#frmMain_so_shortName").attr("disabled","true");
                $("#frmMain_so_idcardType").attr("disabled","true");
                $("#frmMain_so_idcardTDesc").attr("disabled","true");
                $("#frmMain_so_salutationDesc").attr("disabled","true");
                $("#frmMain_so_aliasName").attr("disabled","true");
                $("#frmMain_so_fullName").attr("disabled","true");
                $("#frmMain_so_salutation").attr("disabled","true");
                $("#frmMain_so_birthDate").attr("disabled","true");
                $("#frmMain_so_religion").attr("disabled","true");
                $("#frmMain_so_religionDesc").attr("disabled","true");
                $("#frmMain_so_branchCode").attr("disabled","true");
                $("#frmMain_so_branchCodeName").attr("disabled","true");
                $("#frmMain_so_accessCode").attr("disabled","true");
                $("#frmMain_so_lob").attr("disabled","true");
                $("#frmMain_so_lobDesc").attr("disabled","true");
                $("#frmMain_so_category").attr("disabled","true");
                $("#frmMain_so_cifType").attr("disabled","true");
                $("#frmMain_so_custFirstName").attr("disabled","true");
                $("#frmMain_so_custMidName").attr("disabled","true");
                $("#frmMain_so_custLastName").attr("disabled","true");
                $("#frmMain_so_mailAddrs1").attr("disabled","true");
                $("#frmMain_so_mailAddrs2").attr("disabled","true");
                $("#frmMain_so_mailAddrs3").attr("disabled","true");
                $("#frmMain_so_maillingCode").attr("disabled","true");
                $("#frmMain_so_mailingTownCity").attr("disabled","true");
                $("#frmMain_so_maillingState").attr("disabled","true");
                $("#frmMain_so_maillingStateDesc").attr("disabled","true");
                $("#frmMain_so_maillingCountry").attr("disabled","true");
                $("#frmMain_so_maillingCountryDesc").attr("disabled","true");
                $("#frmMain_so_birthDate").val();
                $("#frmMain_so_maillingTownCityDesc").attr("disabled","true");
                $("#frmMain_so_highRiskmaillingCountry").attr("disabled","true");
                $("#frmMain_so_highRiskmCode").attr("disabled","true");
                $("#frmMain_so_permaAddress1").attr("disabled","true");
                $("#frmMain_so_permaAddress2").attr("disabled","true");
                $("#frmMain_so_permaAddress3").attr("disabled","true");
                $("#frmMain_so_permaZipCode").attr("disabled","true");
                $("#frmMain_so_permaTownCity").attr("disabled","true");
                $("#frmMain_so_permaTownCityDesc").attr("disabled","true");
                $("#frmMain_so_permaState").attr("disabled","true");
                $("#frmMain_so_permaStateDesc").attr("disabled","true");
                $("#frmMain_so_permaCountry").attr("disabled","true");
                $("#frmMain_so_permaCountryDesc").attr("disabled","true");
                $("#frmMain_so_highRiskpermaCountry").attr("disabled","true");
                $("#frmMain_so_highRiskpCode").attr("disabled","true");
                $("#frmMain_so_employerName").attr("disabled","true");
                $("#frmMain_so_employerAddress1").attr("disabled","true");
                $("#frmMain_so_employerAddress2").attr("disabled","true");
                $("#frmMain_so_employerAddress3").attr("disabled","true");
                $("#frmMain_so_employerZipCode").attr("disabled","true");
                $("#frmMain_so_employerTownCity").attr("disabled","true");
                $("#frmMain_so_employerTownDesc").attr("disabled","true");
                $("#frmMain_so_employerState").attr("disabled","true");
                $("#frmMain_so_employerCountry").attr("disabled","true");
                $("#frmMain_so_employementDetails").attr("disabled","true");
                $("#frmMain_so_employerTownCityDesc").attr("disabled","true");
                $("#frmMain_so_monthLyIncomeDesc").attr("disabled","true");
                $("#frmMain_so_relation").attr("disabled","true");
                $("#frmMain_so_holdAddress1").attr("disabled","true");
                $("#frmMain_so_holdAddress2").attr("disabled","true");
                $("#frmMain_so_holdAddress3").attr("disabled","true");
                $("#frmMain_so_holdZipCode").attr("disabled","true");
                $("#frmMain_so_holdTownCity").attr("disabled","true");
                $("#frmMain_so_holdState").attr("disabled","true");
                $("#frmMain_so_holdCountry").attr("disabled","true");
                $("#frmMain_so_nationality").attr("disabled","true");
                $("#frmMain_so_nationalityDesc").attr("disabled","true");
                $("#frmMain_so_countryOfResidence").attr("disabled","true");
                $("#frmMain_so_countryOfResidenceDesc").attr("disabled","true");
                $("#frmMain_so_residencePhoneNo").attr("disabled","true");
                $("#frmMain_so_mobilePhone").attr("disabled","true");
                $("#frmMain_so_lastEdu").attr("disabled","true");
                $("#frmMain_so_lastEduDesc").attr("disabled","true");
                $("#frmMain_so_emailAddress").attr("disabled","true");
                $("#frmMain_so_gender").attr("disabled","true");
                $("#frmMain_so_marStat").attr("disabled","true");
                $("#frmMain_so_marStatDesc").attr("disabled","true");
                $("#frmMain_so_staff").attr("disabled","true");
                // $("#frmMain_so_employeeId").attr("disabled","true");
                $("#frmMain_so_businessType").attr("disabled","true");
                $("#frmMain_so_businessTypeDesc").attr("disabled","true");
                $("#frmMain_so_professionCode").attr("disabled","true");
                $("#frmMain_so_stateIndicator").attr("disabled","true");
                $("#frmMain_so_stateIndicatorDesc").attr("disabled","true");
                $("#frmMain_so_profession").attr("disabled","true");
                $("#frmMain_so_professionDesc").attr("disabled","true");
                $("#frmMain_so_jobTitle").attr("disabled","true");
                $("#frmMain_so_spouseName").attr("disabled","true");
                $("#frmMain_so_aoBusiness").attr("disabled","true");
                $("#frmMain_so_aoOperation").attr("disabled","true");
                $("#frmMain_so_namMother").attr("disabled","true");
                $("#frmMain_so_debtType").attr("disabled","true");
                $("#frmMain_so_debtStatus").attr("disabled","true");
                $("#frmMain_so_branchLocationCode").attr("disabled","true");
                $("#frmMain_so_districtCode").attr("disabled","true");
                $("#frmMain_so_employeICCode").attr("disabled","true");
                $("#frmMain_so_reliwthBank").attr("disabled","true");
                $("#frmMain_so_relition").attr("disabled","true");
                $("#frmMain_so_conWithBank").attr("disabled","true");
                $("#frmMain_so_residenceType").attr("disabled","true");
                $("#frmMain_so_residenceTDesc").attr("disabled","true");
                $("#frmMain_so_idcardType").attr("disabled","true");
                $("#frmMain_so_idcardTDesc").attr("disabled","true");
                // $("#frmMain_so_iCExpiryDate").attr("disabled","true");
                $("#frmMain_so_birthPlace").attr("disabled","true");
                //  $("#frmMain_so_monthLyIncome").attr("disabled","true");
                //  $("#frmMain_so_dataTransXtractFlag").attr("disabled","true");
                $("#frmMain_so_homeStatus").attr("disabled","true");
                $("#frmMain_so_homeStatusDesc").attr("disabled","true");
                $("#frmMain_so_noOfDependant").attr("disabled","true");
                $("#frmMain_so_occupation").attr("disabled","true");
                $("#frmMain_so_occupationDesc").attr("disabled","true");
                $("#frmMain_so_incomeTaxNo").attr("disabled","true");
                //  $("#frmMain_so_RegistrationNo").attr("disabled","true");
                //   $("#frmMain_so_RegistrationDate").attr("disabled","true");
                //  $("#frmMain_so_BusinessCommendate").attr("disabled","true");
                //  $("#frmMain_so_BusinessLicenseNo").attr("disabled","true");
                //   $("#frmMain_so_PlaceOfInc").attr("disabled","true");
                //  $("#frmMain_so_comments").attr("disabled","true");
                //   $("#frmMain_so_typeOfCompany").attr("disabled","true");
                $("#frmMain_so_branchCodeCasa").attr("disabled","true");
                $("#frmMain_so_branchCodeCasaDesc").attr("disabled","true");
                $("#frmMain_so_productCode").attr("disabled","true");
                $("#frmMain_so_productCodeDesc").attr("disabled","true");
                $("#frmMain_so_custId").attr("disabled","true");
                //  $("#frmMain_so_accountOpenDate").attr("disabled","true");
                //  $("#frmMain_so_accountOpenDateConvertion").attr("disabled","true");
                $("#frmMain_so_aoBusinessDesc").attr("disabled","true");
                $("#frmMain_so_aoOperationDesc").attr("disabled","true");
                $("#frmMain_so_persetujuanDataDesc").attr("disabled","true");
                $("#frmMain_so_persetujuanData").attr("disabled","true");
                $("#frmMain_so_communication").attr("disabled","true");
                $("#frmMain_so_communicationDesc").attr("disabled","true");
                $("#frmMain_so_namaKeluarga").attr("disabled","true");
                $("#frmMain_so_aoOperation").attr("disabled","true");
                $("#frmMain_so_acctTitle").attr("disabled","true");
                $("#frmMain_so_flagCIF").attr("disabled","true");
                $("#frmMain_so_greenCard").attr("disabled","true");
                $("#frmMain_so_alamatAS").attr("disabled","true");
                $("#frmMain_so_badanAS").attr("disabled","true");
                $("#frmMain_so_echannel").attr("disabled","true");
                $("#frmMain_so_fatca").attr("disabled","true");
                $("#frmMain_so_tin").attr("disabled","true");
                $("#frmMain_so_tujuanPembukaan").attr("disabled","true");
                $("#frmMain_so_tujuanPembukaanDesc").attr("disabled","true");
                $("#frmMain_so_sumberDana").attr("disabled","true");
                $("#frmMain_so_sumberDanaDesc").attr("disabled","true");
                $("#frmMain_so_sumberDanaLain").attr("disabled","true");
            }
            function nullValueall(){
                $("#frmMain_so_dataTransXtractFlagDesc").val(null);
				
                $("#frmMain_so_applicationID").val(null);
                $("#frmMain_so_cifNumber").val(null);
                $("#frmMain_so_nik").val(null);
                $("#frmMain_so_shortName").val(null);
                $("#frmMain_so_idcardType").val(null);
                $("#frmMain_so_idcardTDesc").val(null);
                $("#frmMain_so_salutationDesc").val(null);
                $("#frmMain_so_aliasName").val(null);
                $("#frmMain_so_fullName").val(null);
                $("#frmMain_so_salutation").val(null);
                $("#frmMain_so_birthDate").val(null);
                $("#frmMain_so_religion").val(null);
                $("#frmMain_so_religionDesc").val(null);
                $("#frmMain_so_branchCode").val(null);
                $("#frmMain_so_branchCodeName").val(null);
                $("#frmMain_so_accessCode").val(null);
                $("#frmMain_so_lob").val(null);
                $("#frmMain_so_lobDesc").val(null);
                $("#frmMain_so_category").val(null);
                $("#frmMain_so_cifType").val(null);
                $("#frmMain_so_custFirstName").val(null);
                $("#frmMain_so_custMidName").val(null);
                $("#frmMain_so_custLastName").val(null);
                $("#frmMain_so_mailAddrs1").val(null);
                $("#frmMain_so_mailAddrs2").val(null);
                $("#frmMain_so_mailAddrs3").val(null);
                $("#frmMain_so_maillingCode").val(null);
                $("#frmMain_so_mailingTownCity").val(null);
                $("#frmMain_so_maillingState").val(null);
                $("#frmMain_so_maillingStateDesc").val(null);
                $("#frmMain_so_maillingCountry").val(null);
                $("#frmMain_so_maillingCountryDesc").val(null);
                $("#frmMain_so_birthDate").val();
                $("#frmMain_so_maillingTownCityDesc").val(null);
                $("#frmMain_so_highRiskmaillingCountry").val(null);
                $("#frmMain_so_highRiskmCode").val(null);
                $("#frmMain_so_permaAddress1").val(null);
                $("#frmMain_so_permaAddress2").val(null);
                $("#frmMain_so_permaAddress3").val(null);
                $("#frmMain_so_permaZipCode").val(null);
                $("#frmMain_so_permaTownCity").val(null);
                $("#frmMain_so_permaTownCityDesc").val(null);
                $("#frmMain_so_permaState").val(null);
                $("#frmMain_so_permaStateDesc").val(null);
                $("#frmMain_so_permaCountry").val(null);
                $("#frmMain_so_permaCountryDesc").val(null);
                $("#frmMain_so_highRiskpermaCountry").val(null);
                $("#frmMain_so_highRiskpCode").val(null);
                $("#frmMain_so_employerName").val(null);
                $("#frmMain_so_employerAddress1").val(null);
                $("#frmMain_so_employerAddress2").val(null);
                $("#frmMain_so_employerAddress3").val(null);
                $("#frmMain_so_employerZipCode").val(null);
                $("#frmMain_so_employerTownCity").val(null);
                $("#frmMain_so_employerTownDesc").val(null);
                $("#frmMain_so_employerState").val(null);
                $("#frmMain_so_employerCountry").val(null);
                $("#frmMain_so_employementDetails").val(null);
                $("#frmMain_so_employerTownCityDesc").val(null);
                $("#frmMain_so_monthLyIncomeDesc").val(null);
                $("#frmMain_so_relation").val(null);
                $("#frmMain_so_holdAddress1").val(null);
                $("#frmMain_so_holdAddress2").val(null);
                $("#frmMain_so_holdAddress3").val(null);
                $("#frmMain_so_holdZipCode").val(null);
                $("#frmMain_so_holdTownCity").val(null);
                $("#frmMain_so_holdState").val(null);
                $("#frmMain_so_holdCountry").val(null);
                $("#frmMain_so_nationality").val(null);
                $("#frmMain_so_nationalityDesc").val(null);
                $("#frmMain_so_countryOfResidence").val(null);
                $("#frmMain_so_countryOfResidenceDesc").val(null);
                $("#frmMain_so_residencePhoneNo").val(null);
                $("#frmMain_so_mobilePhone").val(null);
                $("#frmMain_so_lastEdu").val(null);
                $("#frmMain_so_lastEduDesc").val(null);
                $("#frmMain_so_emailAddress").val(null);
                $("#frmMain_so_gender").val(null);
                $("#frmMain_so_marStat").val(null);
                $("#frmMain_so_marStatDesc").val(null);
                $("#frmMain_so_staff").val(null);
                // $("#frmMain_so_employeeId").val(null);
                $("#frmMain_so_businessType").val(null);
                $("#frmMain_so_businessTypeDesc").val(null);
                $("#frmMain_so_professionCode").val(null);
                $("#frmMain_so_stateIndicator").val(null);
                $("#frmMain_so_stateIndicatorDesc").val(null);
                $("#frmMain_so_profession").val(null);
                $("#frmMain_so_professionDesc").val(null);
                $("#frmMain_so_jobTitle").val(null);
                $("#frmMain_so_spouseName").val(null);
                $("#frmMain_so_aoBusinesss").val(null);
                $("#frmMain_so_aoOperation").val(null);
                $("#frmMain_so_namMother").val(null);
                $("#frmMain_so_debtType").val(null);
                $("#frmMain_so_debtStatus").val(null);
                $("#frmMain_so_branchLocationCode").val(null);
                $("#frmMain_so_districtCode").val(null);
                $("#frmMain_so_employeICCode").val(null);
                $("#frmMain_so_reliwthBank").val(null);
                $("#frmMain_so_relition").val(null);
                $("#frmMain_so_conWithBank").val(null);
                $("#frmMain_so_residenceType").val(null);
                $("#frmMain_so_residenceTDesc").val(null);
                $("#frmMain_so_idcardType").val(null);
                $("#frmMain_so_idcardTDesc").val(null);
                // $("#frmMain_so_iCExpiryDate").val(null);
                $("#frmMain_so_birthPlace").val(null);
                //  $("#frmMain_so_monthLyIncome").val(null);
                //  $("#frmMain_so_dataTransXtractFlag").val(null);
                $("#frmMain_so_homeStatus").val(null);
                $("#frmMain_so_homeStatusDesc").val(null);
                $("#frmMain_so_noOfDependant").val(null);
                $("#frmMain_so_occupation").val(null);
                $("#frmMain_so_occupationDesc").val(null);
                $("#frmMain_so_incomeTaxNo").val(null);
                //  $("#frmMain_so_RegistrationNo").val(null);
                //   $("#frmMain_so_RegistrationDate").val(null);
                //  $("#frmMain_so_BusinessCommendate").val(null);
                //  $("#frmMain_so_BusinessLicenseNo").val(null);
                //   $("#frmMain_so_PlaceOfInc").val(null);
                //  $("#frmMain_so_comments").val(null);
                //   $("#frmMain_so_typeOfCompany").val(null);
                $("#frmMain_so_branchCodeCasa").val(null);
                $("#frmMain_so_branchCodeCasaDesc").val(null);
                $("#frmMain_so_productCode").val(null);
                $("#frmMain_so_productCodeDesc").val(null);
                $("#frmMain_so_custId").val(null);
                //  $("#frmMain_so_accountOpenDate").val(null);
                //  $("#frmMain_so_accountOpenDateConvertion").val(null);
                $("#frmMain_so_aoBusinessDesc").val(null);
                $("#frmMain_so_aoOperationDesc").val(null);
                $("#frmMain_so_persetujuanDataDesc").val(null);
                $("#frmMain_so_persetujuanData").val(null);
                $("#frmMain_so_communication").val(null);
                $("#frmMain_so_communicationDesc").val(null);
                $("#frmMain_so_namaKeluarga").val(null);
                $("#frmMain_so_aoOperation").val(null);
                $("#frmMain_so_acctTitle").val(null);
                $("#frmMain_so_flagCIF").val(null);
                $("#frmMain_so_greenCard").val(null);
                $("#frmMain_so_alamatAS").val(null);
                $("#frmMain_so_badanAS").val(null);
                $("#frmMain_so_echannel").val(null);
                $("#frmMain_so_fatca").val(null);
                $("#frmMain_so_tin").val(null);
                $("#frmMain_so_tujuanPembukaan").val(null);
                $("#frmMain_so_tujuanPembukaanDesc").val(null);
                $("#frmMain_so_sumberDana").val(null);
                $("#frmMain_so_sumberDanaDesc").val(null);
                $("#frmMain_so_sumberDanaLain").val(null);
            }
       
        
            $('#gender-1').click(function() {
                $("#gender-2").attr("checked", false).button("refresh"); 
                $("#frmMain_so_gender").attr("value", "M");
                $("#frmMain_so_genderDesc").attr("value", "MALE");
                $("#frmMain_so_salutationDesc").val(null);
                $("#frmMain_so_salutation").val(null);
            });
            $('#gender-2').click(function() {
                $("#gender-1").attr("checked", false).button("refresh"); 
                $("#frmMain_so_gender").attr("value", "F");
                $("#frmMain_so_genderDesc").attr("value", "FEMALE");
                $("#frmMain_so_salutationDesc").val(null);
                $("#frmMain_so_salutation").val(null);
            });
            $('#fatca-1').click(function() {
                $("#fatca-2").attr("checked", false).button("refresh");
                $("#fatca-3").attr("checked", false).button("refresh");

                $("#frmMain_so_fatca").attr("value", "1");
                $("#frmMain_so_fatcaDesc").attr("value", "Y");
            });
            $('#fatca-2').click(function() {

                $("#fatca-1").attr("checked", false).button("refresh");
                $("#fatca-3").attr("checked", false).button("refresh");
                $("#frmMain_so_fatca").attr("value", "3");
                $("#frmMain_so_fatcaDesc").attr("value", "NA");
            });
            $('#fatca-3').click(function() {

                $("#fatca-1").attr("checked", false).button("refresh");
                $("#fatca-2").attr("checked", false).button("refresh");
                $("#frmMain_so_fatca").attr("value", "2");
                $("#frmMain_so_fatcaDesc").attr("value", "N");
            });
            $('#alamatAS-1').click(function() {
                $("#alamatAS-2").attr("checked", false).button("refresh");

                $("#frmMain_so_fatca").attr("value", "1");
                $("#frmMain_so_fatcaDesc").attr("value", "Y");
            });
            $('#alamatAS-2').click(function() {

                $("#alamatAS-1").attr("checked", false).button("refresh");
                $("#frmMain_so_alamatAS").attr("value", "2");
                $("#frmMain_so_alamatASDesc").attr("value", "N");
            });
            $('#greenCard-1').click(function() {
                $("#greenCard-2").attr("checked", false).button("refresh");

                $("#frmMain_so_greenCard").attr("value", "1");
                $("#frmMain_so_greenCardDesc").attr("value", "Y");
            });
            $('#greenCard-2').click(function() {

                $("#greenCard-1").attr("checked", false).button("refresh");
                $("#frmMain_so_greenCard").attr("value", "2");
                $("#frmMain_so_greenCardDesc").attr("value", "N");
            });
            $('#badanAS-1').click(function() {
                $("#badanAS-2").attr("checked", false).button("refresh");

                $("#frmMain_so_badanAS").attr("value", "1");
                $("#frmMain_so_badanASDesc").attr("value", "Y");
            });
            $('#badanAS-2').click(function() {

                $("#badanAS-1").attr("checked", false).button("refresh");
                $("#frmMain_so_badanAS").attr("value", "2");
                $("#frmMain_so_badanASDesc").attr("value", "N");
            });
            $('#echannel-1').click(function() {
                $("#echannel-2").attr("checked", false).button("refresh");

                $("#frmMain_so_echannel").attr("value", "1");
                $("#frmMain_so_echannelDesc").attr("value", "Y");
            });
            $('#echannel-2').click(function() {

                $("#echannel-1").attr("checked", false).button("refresh");
                $("#frmMain_so_echannel").attr("value", "2");
                $("#frmMain_so_echannelDesc").attr("value", "N");
            });
            function myDialog(eButton, title, eDialog, eId, eDesc, closeFunction) {
                return function() {
                    if (!(
                    (eButton.button('option').disabled != undefined) &&
                        (eButton.button('option', 'disabled'))
                )) {
                        dlgParams = {};
                        dlgParams.id = eId;
                        dlgParams.desc = eDesc;
                        var $tmp = $("#ph-dlg").dialog("option", "title");
                        $("#ph-dlg").dialog("option", "position", "center");
                        $("#ph-dlg").dialog("option", "width", ($("body").width() * (3 / 4)));
                        $("#ph-dlg").dialog("option", "height", 450);
                        $("#ph-dlg")
                        .html("Please wait...")
                        .unbind("dialogopen")
                        .bind("dialogopen", function() {
                            $("#" + eDialog).click();
                        })
                        .unbind("dialogclose")
                        .bind("dialogclose", function() {
                            $(this).dialog({title: $tmp});
                            if (closeFunction != undefined)
                                closeFunction();
                        })
                        .dialog({
                            title: title
                        })
                        .dialog("open");
                    }
                };
            }
            ;
            function myDialogLike(eButton, title, eDialog, eId, eDesc, eLike, strLike, closeFunction) {

                return function() {
                    if (!(
                    (eButton.button('option').disabled != undefined) &&
                        (eButton.button('option', 'disabled'))
                )) {
                        dlgParams = {};
                        dlgParams.id = eId;
                        dlgParams.desc = eDesc;
                        var $tmp = $("#ph-dlg").dialog("option", "title");
                        $("#ph-dlg").dialog("option", "position", "center");
                        $("#ph-dlg").dialog("option", "width", ($("body").width() * (3 / 4)));
                        $("#ph-dlg").dialog("option", "height", 450);
                        $("#ph-dlg")
                        .html("Please wait...")
                        .unbind("dialogopen")
                        .bind("dialogopen", function() {
                            console.log('eLike:' + eLike + ', strLike: ' + strLike);
                            $("#" + eLike).attr("value", $('#' + strLike).val());
                            $("#" + eDialog).click();
                        })
                        .unbind("dialogclose")
                        .bind("dialogclose", function() {
                            $(this).dialog({title: $tmp});
                            if (closeFunction != undefined){
                                closeFunction();} else {
                                if($('#'+dlgParams.id).val() != 'EKTP'){
                                    var arrayLab = new Array();
                                    //alert("LIKECODE :" + $('#'+dlgParams.id).val());
                                    arrayLab = eId.split("_");
                                    //alert("CODE :" +arrayLab);
                                    var namlement = "error_"+arrayLab[2];
                                    //alert("Element :" + namlement);
                                    removeValidation(namlement);
                                }
                                if(arrayLab[2]=='aoBusiness'){
                                    //alert("BUSS");
                                    $("#frmMain_so_aoOperation").val($("#frmMain_so_aoBusiness").val());
                                    $("#frmMain_so_aoOperationDesc").val($("#frmMain_so_aoBusinessDesc").val());
                                } 
                                else if(arrayLab[2]=='branchCodeCasa'){
                                    //alert("BUSS");
                                    $("#frmMain_so_productCode").val(null);
                                    $("#frmMain_so_productCodeDesc").val(null);
                                }
                            }
                        })
                        .dialog({
                            title: title
                        })
                        .dialog("open");
                    }
                };
            }
            ;
            function myDialogProduct(eButton, title, eDialog, eId, eDesc, eLike, strLike, closeFunction) {

                return function() {
                    if (!(
                    (eButton.button('option').disabled != undefined) &&
                        (eButton.button('option', 'disabled'))
                )) {
                        dlgParams = {};
                        dlgParams.id = eId;
                        dlgParams.desc = eDesc;
                        var $tmp = $("#ph-dlg").dialog("option", "title");
                        $("#ph-dlg").dialog("option", "position", "center");
                        $("#ph-dlg").dialog("option", "width", ($("body").width() * (3 / 4)));
                        $("#ph-dlg").dialog("option", "height", 450);
                        $("#ph-dlg")
                        .html("Please wait...")
                        .unbind("dialogopen")
                        .bind("dialogopen", function() {
                            var prodCode=$("#frmMain_so_productCode").val();
					
                            if(prodCode==''){					
                                $("#frmMain_brnProd").val("%" + "|" + $("#frmMain_so_branchCode").val());
                            } else {
                                $("#frmMain_brnProd").val($("#frmMain_so_productCode").val() + "|" + $("#frmMain_so_branchCode").val());
                            }
								
                            console.log('eLike:' + eLike + ', strLike: ' + strLike);
                            $("#" + eLike).attr("value", $('#' + strLike).val());
                            $("#" + eDialog).click();
                        })
                        .unbind("dialogclose")
                        .bind("dialogclose", function() {
                            $(this).dialog({title: $tmp});
                            if (closeFunction != undefined){
                                closeFunction();} else {
                                if($('#'+dlgParams.id).val() != 'EKTP'){
                                    var arrayLab = new Array();
                                    //alert("LIKECODE :" + $('#'+dlgParams.id).val());
                                    arrayLab = eId.split("_");
                                    //alert("CODE :" +arrayLab);
                                    var namlement = "error_"+arrayLab[2];
                                    //alert("Element :" + namlement);
                                    removeValidation(namlement);
                                }
                                if(arrayLab[2]=='aoBusiness'){
                                    //alert("BUSS");
                                    $("#frmMain_so_aoOperation").val($("#frmMain_so_aoBusiness").val());
                                    $("#frmMain_so_aoOperationDesc").val($("#frmMain_so_aoBusinessDesc").val());
                                } 
                                else if(arrayLab[2]=='branchCodeCasa'){
                                    //alert("BUSS");
                                    $("#frmMain_so_productCode").val(null);
                                    $("#frmMain_so_productCodeDesc").val(null);
                                }
                            }
                        })
                        .dialog({
                            title: title
                        })
                        .dialog("open");
                    }
                };
            }
            ;
            function myDialogRisk(eButton, title, eDialog, eId, eDesc, eRiskCode,eRiskDesc, eLike, strLike, closeFunction) {

                return function() {
                    if (!(
                    (eButton.button('option').disabled != undefined) &&
                        (eButton.button('option', 'disabled'))
                )) {
                        dlgParams = {};
                        dlgParams.id = eId;
                        dlgParams.desc = eDesc;
                        dlgParams.riskcode = eRiskCode;
                        dlgParams.riskdesc = eRiskDesc;

                        var $tmp = $("#ph-dlg").dialog("option", "title");
                        $("#ph-dlg").dialog("option", "position", "center");
                        $("#ph-dlg").dialog("option", "width", ($("body").width() * (3 / 4)));
                        $("#ph-dlg").dialog("option", "height", 450);
                        $("#ph-dlg")
                        .html("Please wait...")
                        .unbind("dialogopen")
                        .bind("dialogopen", function() {
                            console.log('eLike:' + eLike + ', strLike: ' + strLike);
                            $("#" + eLike).attr("value", $('#' + strLike).val());
                            $("#" + eDialog).click();
                            $("#frmMain_brnLOB").val($("#frmMain_so_lobDesc").val() + "|" + $("#frmMain_so_branchCode").val());
                        })
                        .unbind("dialogclose")
                        .bind("dialogclose", function() {
                            $(this).dialog({title: $tmp});
                            //alert("high risk");
                            if (closeFunction != undefined) {
                                closeFunction();} else {
                                if($('#'+dlgParams.id).val()!='EKTP'){
                                    var arrayMap = new Array();
                                    //alert("LIKECODE :" + eId);
                                    arrayMap = eId.split("_");
                                    //alert("CODE :" +arrayMap);
                                    var namlementKTP = "error_"+arrayMap[2];
                                    //alert("Element :" + namlementKTP);
                                    removeValidation(namlementKTP);
                                }
                                //alert("high risk :" + closeFunction);
                                var arrayLab = new Array();
                                //alert("riskcode :" + dlgParams.riskcode);
                                arrayLab = dlgParams.riskcode.split("_");
                                //alert("high risk :" +arrayLab);
                                var namlement = "error_"+arrayLab[2];
                                //alert("Element :" + namlement);
                                removeValidation(namlement);
                                var hrType = $("#"+dlgParams.riskdesc).val();
                                var generalHR = eRiskDesc.substr(11,eRiskDesc.length-4);
                                //alert($("#"+dlgParams.riskcode).val());
                                if($("#"+dlgParams.riskcode).val() == '' || $("#"+dlgParams.riskcode).val() == null) {
                                    //alert("NON HiGHRISK : " + $("#"+dlgParams.riskcode).val());
                                    $("#frmMain_"+generalHR+"TypePEP").val(null);
                                    $("#frmMain_"+generalHR+"Flag").val(null);
                                    $("#frmMain_"+generalHR+"Type").val(null);
                                } else {
                                    //alert("HiGHRISK :" + $("#"+dlgParams.riskcode).val()); 
								
                                    var trueElement = document.getElementById(arrayLab[2]);                              
                                    //alert("2 :" +trueElement);
                                    $('.wwFormTable tbody tr#' + arrayLab[2]).before('<tr id="error_'+arrayLab[2]+'"><td align="left" colspan="5"><span class="errorMessage" classname="errorMessage">HighRisk Customer :'+$("#"+dlgParams.riskcode).val() + ' ' + $("#"+dlgParams.riskdesc).val()+ ' lakukan proses EDD/CDD</span></td></tr>');
                                    
                                    //alert(hrType);
                                    //alert(hrType.substr(0,3));
                                    //alert("#frmMain_" + eRiskDesc.substr(11,eRiskDesc.length-4)+"Flag");
                                    if(hrType.substr(0,3)=="PEP") {
                                        $("#frmMain_"+generalHR+"Flag").val("PEP");	
                                        $("#frmMain_"+generalHR+"TypePEP").val("Politically Exposed Person");
                                    } else {
                                        $("#frmMain_"+generalHR+"Flag").val("HR");
                                        if("highRiskprofession" == generalHR){
                                            $("#frmMain_"+generalHR+"Type").val('High Risk Profession');	
                                        } else if("highRiskbusiness" == generalHR){
                                            $("#frmMain_"+generalHR+"Type").val('High Risk Business');	
                                        } else if("highRisknationality" == generalHR){
                                            $("#frmMain_"+generalHR+"Type").val('High Risk Country');	
                                        } else {
                                            $("#frmMain_"+generalHR+"Type").val(null);
                                        }
                                    }
                                }
                            }
                        })
                        .dialog({
                            title: title
                        })
                        .dialog("open");
                    }
                };
            }
            ;
            function myDialogParam(eButton, title, eDialog, eId, eDesc, eLike, strLike, eId, closeFunction) {
                return function() {
                    if (!(
                    (eButton.button('option').disabled != undefined) &&
                        (eButton.button('option', 'disabled'))
                )) {
                        dlgParams = {};
                        dlgParams.id = eId;
                        dlgParams.desc = eDesc;
                        var $tmp = $("#ph-dlg").dialog("option", "title");
                        $("#ph-dlg").dialog("option", "position", "center");
                        $("#ph-dlg").dialog("option", "width", ($("body").width() * (3 / 4)));
                        $("#ph-dlg").dialog("option", "height", 450);
                        $("#ph-dlg")
                        .html("Please wait...")
                        .unbind("dialogopen")
                        .bind("dialogopen", function() {
                            $("#" + eLike).attr("value", $('#' + strLike).val());
                            $("#" + eId).attr("value");
                            $("#" + eDialog).click();


                        })
                        .unbind("dialogclose")
                        .bind("dialogclose", function() {
                            $(this).dialog({title: $tmp});
                            if (closeFunction != undefined){
                                closeFunction();} else {
                                if($('#'+dlgParams.id).val() != ''){
                                    var arrayLab = new Array();
                                    //alert("LIKECODE :" + $('#'+dlgParams.id).val());
                                    arrayLab = eId.split("_");
                                    //alert("CODE :" +arrayLab);
                                    var namlement = "error_"+arrayLab[2];
                                    //alert("Element :" + namlement);
                                    removeValidation(namlement);
                                }}
                        })
                        .dialog({
                            title: title
                        })
                        .dialog("open");
                    }
                };
            }
            ;

            $("#btnLookupSalutation").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.salutation" />', 'aDlgSalutation',
            'frmMain_so_salutation', 'frmMain_so_salutationDesc',
            'frmDlgSalutation_term', 'frmMain_so_gender'
        ));

            $("#btnLookupHomeStatus").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.homeStatus" />', 'aDlgHomeStatus',
            'frmMain_so_homeStatus', 'frmMain_so_homeStatusDesc',
            'frmDlgHomeStatus_term', ''
        )
        );
            $("#btnLookupMailingTownCity").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.kota" />', 'aDlgMaiilingTownCity',
            'frmMain_so_maillingTownCity', 'frmMain_so_maillingTownCityDesc', 'frmDlgMaiilingTownCity_term', 'frmMain_so_maillingTownCityDesc'
        )
        );

            $("#btnLookupPermaTownCity").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.kota" />', 'aDlgPermaTownCity',
            'frmMain_so_permaTownCity', 'frmMain_so_permaTownCityDesc', 'frmDlgPermaTownCity_term', 'frmMain_so_permaTownCityDesc'
        ));
            $("#btnLookupMailingState").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.provinsi" />', 'aDlgMailingState',
            'frmMain_so_maillingState', 'frmMain_so_maillingStateDesc', 'frmDlgMailingState_term', 'frmMain_so_maillingStateDesc'
        ));
            $("#btnLookupNilaiTransaksi").click(myDialogLike(
            $(this), '<s:text name="label.pekerjaan.nilaitransaksi" />', 'aDlgNilaiTransaksi',
            'frmMain_so_expectedLimit', 'frmMain_so_expectedLimitDesc', 'frmDlgNilaiTransaksi_term', ''
        ));
            $("#btnLookupPermaState").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.provinsi" />', 'aDlgPermaState',
            'frmMain_so_permaState', 'frmMain_so_permaStateDesc', 'frmDlgPermaState_term', 'frmMain_so_permaStateDesc'
        ));
            $("#btnLookupProfession").click(myDialogRisk(
            $(this), '<s:text name="label.datatambahan.pekerjaan" />', 'aDlgProfession',
            'frmMain_so_profession', 'frmMain_so_professionDesc','frmMain_so_highRiskprofessionCode','frmMain_so_highRiskprofession', 'frmDlgProfession_term', 'frmMain_so_professionDesc'
        ));
            $("#btnLookupAoBusiness").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.aobusiness" />', 'aDlgAoBusiness',
            'frmMain_so_aoBusiness', 'frmMain_so_aoBusinessDesc', 'frmDlgAoBusiness_term', 'frmMain_so_aoBusiness'
        ));

            $("#btnLookupMaritalStatus").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.statusperkawinan" />', 'aDlgMaritalStatus',
            'frmMain_so_marStat', 'frmMain_so_marStatDesc','frmDlgMaritalStatus_term',''
        ));

            $("#btnLookupAoOperation").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.aooperation" />', 'aDlgAoOperation',
            'frmMain_so_aoOperation', 'frmMain_so_aoOperationDesc', 'frmDlgAoOperation_term', 'frmMain_so_aoOperation'
        ));
            $("#btnLookupCustEducation").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.pend" />', 'aDlgCustEducation',
            'frmMain_so_lastEdu', 'frmMain_so_lastEduDesc','frmDlgCustEducation_term',''
        ));
            $("#btnLookupReligion").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.agama" />', 'aDlgReligion',
            'frmMain_so_religion', 'frmMain_so_religionDesc','frmDlgReligion_term',''
        ));
            $("#btnLookupJnsKartuIden").click(myDialogLike(
            $(this), '<s:text name="label.nokartu.iden" />', 'aDlgIDType',
            'frmMain_so_idcardType', 'frmMain_so_idcardTDesc','frmDlgIDType_term',''
        ));

            $("#btnLookupPersetujuanData").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.persetujuandata" />', 'aDlgPersetujuanData',
            'frmMain_so_dataTransXtractFlag', 'frmMain_so_dataTransXtractFlagDesc',
            'frmDlgPersetujuanData_term',''
        ));
            $("#btnLookupCommunication").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.persetujuandata" />', 'aDlgCommunication',
            'frmMain_so_communication', 'frmMain_so_communicationDesc',
            'frmDlgCommunication_term',''
        ));
                        
            $("#btnLookupStateIndicator").click(myDialogLike(
            $(this), '<s:text name="label.pembukaan.indicator" />', 'aDlgStatIndikator',
            'frmMain_so_stateIndicator', 'frmMain_so_stateIndicatorDesc',
            'frmDlgStatIndikator_term',''
        ));
            $("#btnLookupTujuan").click(myDialogLike(
            $(this), '<s:text name="label.pembukaan.tujuan" />', 'aDlgTujuan',
            'frmMain_so_tujuan', 'frmMain_so_tujuanDesc',
            'frmDlgTujuan_term',''
        ));
            $("#btnLookupSumberDana").click(myDialogLike(
            $(this), '<s:text name="label.pembukaan.sumberdana" />', 'aDlgSumberDana',
            'frmMain_so_sumberDana', 'frmMain_so_sumberDanaDesc',
            'frmDlgSumberDana_term',''
        ));

            $("#btnLookupPendapatan").click(myDialogLike(
            $(this), '<s:text name="label.pekerjaan.pendapatan" />', 'aDlgPendapatan',
            'frmMain_so_monthLyIncome', 'frmMain_so_monthLyIncomeDesc',
            'frmDlgPendapatan_term',''
        ));
            $("#btnLookupBusinessType").click(myDialogRisk(
            $(this), '<s:text name="label.pekerjaan.bidang" />', 'aDlgBusinessType',
            'frmMain_so_businessType', 'frmMain_so_businessTypeDesc', 'frmMain_so_highRiskbusinessCode','frmMain_so_highRiskbusiness', 'frmDlgBusinessType_term', 'frmMain_so_businessTypeDesc'
        ));

            $("#btnLookupOfficeCity").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.kota" />', 'aDlgEmployerCity',
            'frmMain_so_employerTownCity', 'frmMain_so_employerTownCityDesc', 'frmDlgEmployerCity_term', 'frmMain_so_employerTownCityDesc'
        ));
            $("#btnLookupNationality").click(myDialogRisk(
            $(this), '<s:text name="label.nasabah.wni" />', 'aDlgNationality',
            'frmMain_so_nationality', 'frmMain_so_nationalityDesc', 'frmMain_so_highRisknationalityCode', 'frmMain_so_highRisknationality', 'frmDlgNationality_term', 'frmMain_so_nationalityDesc'
        ));
            $("#btnLookupCountry").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.country" />', 'aDlgCountry',
            'frmMain_so_countryOfResidence', 'frmMain_so_countryOfResidenceDesc', 'frmDlgCountry_term', 'frmMain_so_countryOfResidenceDesc'
        ));
            $("#btnLookupPermaCountry").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.country" />', 'aDlgPermaCountry',
            'frmMain_so_permaCountry', 'frmMain_so_permaCountryDesc', 'frmDlgPermaCountry_term', 'frmMain_so_permaCountryDesc'
        ));
            $("#btnLookupMailCountry").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.country" />', 'aDlgMailCountry',
            'frmMain_so_maillingCountry', 'frmMain_so_maillingCountryDesc', 'frmDlgMailCountry_term', 'frmMain_so_maillingCountryDesc'
        ));
            $("#btnLookupOccupation").click(myDialogLike(
            $(this), '<s:text name="label.pekerjaan.pangkat" />', 'aDlgOccupation',
            'frmMain_so_occupation', 'frmMain_so_occupationDesc', 'frmDlgOccupation_term', 'frmMain_so_occupationDesc'
        ));
            $("#btnLookupTujuanPembukaan").click(myDialogLike(
            $(this), '<s:text name="label.pembukaan.tujuan" />', 'aDlgTujuanPembukaan',
            'frmMain_so_tujuanPembukaan', 'frmMain_so_tujuanPembukaanDesc', 'frmDlgTujuanPembukaan_term', ''
        ));
            $("#btnLookupLob").click(myDialogLike(
            $(this), '<s:text name="label.pembukaan.lobcode" />', 'aDlgLob',
            'frmMain_so_lob', 'frmMain_so_lobDesc', 'frmDlgLob_term', 'frmMain_so_branchCodeCasa'
        ));
            $("#btnLookupBranchCode").click(myDialogLike(
            $(this), '<s:text name="label.nasabah.kodecab" />', 'aDlgAcctBranch',
            'frmMain_so_branchCodeCasa', 'frmMain_so_branchCodeCasaDesc', 'frmDlgAcctBranch_term', 'frmMain_so_branchCodeCasa'
        ));
            $("#btnLookupProduct").click(myDialogProduct(
            $(this),  '<s:text name="label.pembukaan.productname" />', 'aDlgProduct',
            'frmMain_so_productCode', 'frmMain_so_productCodeDesc', 'frmDlgProduct_term', 'frmMain_brnProd'
        ));
            $("#frmMain_so_tujuanPembukaanDesc").die('keydown');
            $("#frmMain_so_tujuanPembukaanDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    $("#btnLookupTujuanPembukaan").click();
                }
            });
            
            $("#frmMain_so_marStatDesc").die('keydown');
            $("#frmMain_so_marStatDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false'){
                        $("#btnLookupMaritalStatus").click();
                    }
                }
            });
            $("#frmMain_so_permaCountryDesc").die('keydown');
            $("#frmMain_so_permaCountryDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false'){
                        $("#btnLookupPermaCountry").click();
                    }
                }
            });
            $("#frmMain_so_maillingCountryDesc").die('keydown');
            $("#frmMain_so_maillingCountryDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false'){
                        $("#btnLookupMailCountry").click();
                    }
                }
            });

            
            $("#frmMain_so_productCode").die('keydown');
            $("#frmMain_so_productCode").live('keydown', function(e) {               
                if (e.keyCode == 9) {
                    $("#frmMain_brnProd").val($("#frmMain_so_productCode").val() + "|" + $("#frmMain_so_branchCode").val());
                    $("#btnLookupProduct").click();
                }
            });
            $("#frmMain_so_occupationDesc").die('keydown');
            $("#frmMain_so_occupationDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false'){
                        $("#btnLookupOccupation").click();
                    }
				
                }
            });
            $("#frmMain_so_expectedLimitDesc").die('keydown');
            $("#frmMain_so_expectedLimitDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupNilaiTransaksi").click();
                    }
                
                }
            });
            $("#frmMain_so_homeStatusDesc").die('keydown');
            $("#frmMain_so_homeStatusDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupHomeStatus").click();
                    }
                }
            });

            $("#frmMain_so_countryOfResidenceDesc").die('keydown');
            $("#frmMain_so_countryOfResidenceDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupCountry").click();
                    }
                }
            });
            $("#frmMain_so_branchCodeCasa").die('keydown');
            $("#frmMain_so_branchCodeCasa").live('keydown', function(e) {

                if (e.keyCode == 9) {
                    $("#btnLookupBranchCode").click();
                }
            });
            $("#frmMain_so_nationalityDesc").die('keydown');
            $("#frmMain_so_nationalityDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupNationality").click();
                    }
                }
            });
            $("#frmMain_so_employerTownCityDesc").die('keydown');
            $("#frmMain_so_employerTownCityDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupOfficeCity").click();
                    }
                }
            });
            $("#frmMain_so_businessTypeDesc").die('keydown');
            $("#frmMain_so_businessTypeDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupBusinessType").click();
                    }
                }
            });

            $("#frmMain_so_monthLyIncomeDesc").die('keydown');
            $("#frmMain_so_monthLyIncomeDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupPendapatan").click();
                    }
                }
            });
            $("#frmMain_so_lobDesc").die('keydown');
            $("#frmMain_so_lobDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    $("#frmMain_brnLOB").val($("#frmMain_so_lobDesc").val() + "|" + $("#frmMain_so_branchCode").val());
                    $("#btnLookupLob").click();
                }
            });
            $("#frmMain_so_sumberDanaDesc").die('keydown');
            $("#frmMain_so_sumberDanaDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    $("#btnLookupSumberDana").click();
                }
            });
            $("#frmMain_so_tujuanDesc").die('keydown');
            $("#frmMain_so_tujuanDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    $("#btnLookupTujuan").click();
                }
            });

            $("#frmMain_so_dataTransXtractFlagDesc").die('keydown');
            $("#frmMain_so_dataTransXtractFlagDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupPersetujuanData").click();
                    }
                }
            });
            $("#frmMain_so_communicationDesc").die('keydown');
            $("#frmMain_so_communicationDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupCommunication").click();
                    }
                }
            });
            
            $("#frmMain_so_idcardTDesc").die('keydown');
            $("#frmMain_so_idcardTDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false'){
                        $("#btnLookupJnsKartuIden").click();
                    }
                }
            });
            $("#frmMain_so_religionDesc").die('keydown');
            $("#frmMain_so_religionDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false'){
                        $("#btnLookupReligion").click();
                    }
                }
            });
            $("#frmMain_so_aoOperation").die('keydown');
            $("#frmMain_so_aoOperation").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupAoOperation").click();
                    }
                }
            });
            $("#frmMain_so_aoBusiness").die('keydown');
            $("#frmMain_so_aoBusiness").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupAoBusiness").click();
                    }
                }
            });

            $("#frmMain_so_professionDesc").die('keydown');
            $("#frmMain_so_professionDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupProfession").click();
                    }
                }
            });
            $("#frmMain_so_permaStateDesc").die('keydown');
            $("#frmMain_so_permaStateDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupPermaState").click();
                    }
                }
            });  
            $("#frmMain_so_maillingStateDesc").die('keydown');
            $("#frmMain_so_maillingStateDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupMailingState").click();
                    }
                }
            });
            $("#frmMain_so_permaTownCityDesc").die('keydown');
            $("#frmMain_so_permaTownCityDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupPermaTownCity").click();
                    }
                }
            });
            $("#frmMain_so_maillingTownCityDesc").die('keydown');
            $("#frmMain_so_maillingTownCityDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupMailingTownCity").click();
                    }
                }
            });
            $("#frmMain_so_maillingTownCityDesc").die('keydown');
            $("#frmMain_so_maillingTownCityDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupMailingTownCity").click();
                    }
                }
            });

            $("#frmMain_so_salutationDesc").die('keydown');
            $("#frmMain_so_salutationDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupSalutation").click();
                    }
                }
            });
            $("#frmMain_so_lastEduDesc").die('keydown');
            $("#frmMain_so_lastEduDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupCustEducation").click();
                    }
                }
            });
            $("#frmMain_so_stateIndicatorDesc").die('keydown');
            $("#frmMain_so_stateIndicatorDesc").live('keydown', function(e) {
                if (e.keyCode == 9) {
                    if($("#frmMain_so_flagCIF").val()=='false') {
                        $("#btnLookupStateIndicator").click();
                    }
                }
            });

            $("#btnCancelCasa").unsubscribe("beforeCasa")
            .subscribe("beforeCasa", function(event, data) {
                //alert("CANCEL");
                event.originalEvent.options.submit = false;
                var current = "1";      
                $("#frmGo_idMenu").val("26301");
                $("#frmGo_goAction").val("exec");
                $("#btnGo").click();
            });			

			
            $("#frmMain_so_emailAddress" ).change(function() {
                //alert( "replicate EMAIL" );
                if(validateForm_Email()==false){
                    messageBox(1, 'INVALID EMAIL ADDRESS');
                }
            });
            $("#frmMain_so_fullName" ).change(function() {
                //alert( "replicate name" );
                $("#frmMain_so_acctTitle").attr("value",$("#frmMain_so_fullName").val());
                $("#frmMain_so_shortName").attr("value",$("#frmMain_so_fullName").val());
            });

            $("#frmMain_so_aoBusinessDesc" ).change(function() {
                //alert( "replicate AO" );
                $("#frmMain_so_aoOperationDesc").attr("value",$("#frmMain_so_aoBusinessDesc").val());
                $("#frmMain_so_aoOperation").attr("value",$("#frmMain_so_aoOperation").val());
            });
		
            function DatabaseMapper(){
                //$("#frmMain_so_maillingTownCity").val($("#frmMain_so_maillingTownCityDesc").val());
                //$("#frmMain_so_maillingState").val($("#frmMain_so_maillingStateDesc").val());
                //$("#frmMain_so_permaTownCity").val($("#frmMain_so_permaTownCityDesc").val());
                //$("#frmMain_so_permaState").val($("#frmMain_so_permaStateDesc").val());
                //alert("DEFAULT MAPPING");
                $('#frmMain_so_menuAccount').val('26302');
                $('#frmMain_so_cifType').val("C");
                $('#frmMain_so_category').val('A');
                $('#frmMain_so_shortName').val($("#frmMain_so_fullName").val());
                $('#frmMain_so_professionCode').val($("#frmMain_so_profession").val());
                $('#frmMain_so_countryOfResidenceDesc').val($("#frmMain_so_nationalityDesc").val());
                $('#frmMain_so_countryOfResidence').val($("#frmMain_so_nationality").val());
                $('#frmMain_so_maillingCountryDesc').val($("#frmMain_so_nationalityDesc").val());
                $('#frmMain_so_maillingCountry').val($("#frmMain_so_nationality").val());
                $('#frmMain_so_permaCountryDesc').val($("#frmMain_so_nationalityDesc").val());
                $('#frmMain_so_permaCountry').val($("#frmMain_so_nationality").val());
                $('#frmMain_so_holdTownCity').val($("#frmMain_so_permaTownCityDesc").val());
                $('#frmMain_so_holdState').val($("#frmMain_so_permaStateDesc").val());
                $('#frmMain_so_holdCountry').val($("#frmMain_so_nationality").val());
                $('#frmMain_so_holdCountryDesc').val($("#frmMain_so_nationalityDesc").val());
                $('#frmMain_so_sandiDatiII').val($("#frmMain_so_permaTownCity").val());
            }
            ////end 
            $("#btnCreate").unsubscribe("beforeSubmitCasa");
            $("#btnCreate").subscribe("beforeSubmitCasa", function(event) {
                var messaging = "Please Wait Your Request Being Processed..";
                //$("#frmMain").unbind("submit");
                event.originalEvent.options.submit = false; 
                var existingCif = $("#frmMain_so_flagCIF").val().toString();
                var processed = false;
                var hrFlag = '0000';
                var realMessage = '';
			
                //alert(existingCif);
                $("#frmMain_verified").val(0);
			
                if (existingCif == 'true'){
                    //alert(1);
                    if(validateForm_frmMainCasaExisting()){
                        processed = true;
                    } else {
                        messageBox(1, 'Please fill All Mandatory Field');
                    }
                } else if(existingCif == 'false'){
                    //alert(2);
                    if(validateForm_frmMainCasa()){
                        if(validateForm_ktpMapping()){
                            //alert("KTP MAPPING VLIDATION");
                            processed= true;
                        } else {
                            messageBox(1, 'Please fill All Mandatory Field');
                        }
                    } else {
                        messageBox(1, 'Please fill All Mandatory Field', function(){$("#ph-main").scrollTop(0);});
                    }
                }
                //alert("BEFORE SUBMIT");
                hrFlag = validateHighRisk();
                if(hrFlag != '0000'){
                    var flgD = hrFlag.substr(3, 1);
                    var HR1 = "null";
                    var HR2 = "null";
                    var HR3 = "null";
                    var PEP = "null";
                    HR1 = $("#frmMain_highRiskprofessionType").val();
                    HR2 = $("#frmMain_highRisknationalityType").val();
                    HR3 = $("#frmMain_highRiskbusinessType").val();
                    PEP = $("#frmMain_highRiskprofessionTypePEP").val();
                    if(HR1 != null){
                        HR1 = HR1 + '\n';
                    }
                    if(HR2 != null){
                        HR2 = HR2 + '\n';
                    }
                    if(HR3 != null){
                        HR3 = HR3 + '\n';
                    }
                    if(PEP != null){
                        PEP = PEP + '\n';
                    }
                    //alert(HR1);
                    var PEPmessage = '<s:text name="26301.highRisk.PEP.Message" ><s:param>'+PEP+'</s:param><s:param>'+HR1+'</s:param><s:param>'+HR2+'</s:param><s:param>'+HR3+'</s:param></s:text>';
                    var HRmessage = '<s:text name="26301.highRisk.PEP.Message" ><s:param>High Risk Customer</s:param><s:param>'+HR1+'</s:param><s:param>'+HR2+'</s:param><s:param>'+HR3+'</s:param></s:text>';
                    //alert(hrFlag);
                    //alert(flgD);
                    if(flgD == '1') {
                        // PEP high Risk Type
                        //alert(PEPmessage);
                        realMessage = PEPmessage;	
                    } else {
                        //alert(HRmessage);
                        realMessage = HRmessage;
                    }
                }
                //var trueElement = document.getElementById("error_" + arrayLab[2]);
                //alert("MID STEP BEFORE :" + existingCif);
                //alert("MID STEP :" + processed);
                if(existingCif == 'false' && processed == true && hrFlag == '0000'){
                    DatabaseMapper();
                    messageBoxOkCancel(1, 'This Is New Customer Press "OK" to Continue ',function(event){
                        var flags = "2";
                        //alert("FLAG1 :" + processed);
			
                        if(processed){
                            //alert(4);
                            dlgParams = {};
                            dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                            dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                            dlgParams.event = event;
                            //event.originalEvent.options.submit = true;       
                            $('#frmMain_flagButton').val(flags.toString());
                            $("#btnCreate").unsubscribe("beforeSubmitCasa");
                            $("#btnCreate").click();
                            $("#btnCreate").attr("disabled","disabled");
                            $("#spinner").removeClass("ui-helper-hidden");
                            waitingMessage(3,messaging,"divwaitingRespond");
                        }
                    },function(){});
                } else if (existingCif == 'true' && processed== true && hrFlag == '0000') {
                    messageBoxOkCancel(1, 'This Is Existing Customer Press "OK" to Continue ',function(event){
                        var flags = "2";
                        //alert("FLAG1 :" + processed);
			
                        if(processed){			
                            //alert(4);
                            dlgParams = {};
                            dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                            dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                            dlgParams.event = event;
                            //event.originalEvent.options.submit = true;       
                            $('#frmMain_flagButton').val(flags.toString());
                            $("#btnCreate").unsubscribe("beforeSubmitCasa");
                            $("#btnCreate").click();
                            $("#btnCreate").attr("disabled","disabled");
                            $("#spinner").removeClass("ui-helper-hidden");
                            waitingMessage(3,messaging,"divwaitingRespond");
                        }});
                } else if(existingCif == 'false' && processed == true && hrFlag != '0000'){
                    DatabaseMapper();
                    messageBoxParam('divInnerMessage',3, realMessage, function(){messageBoxOkCancel(1, 'This Is New Customer Press "OK" to Continue ',function(event){
                            var flags = "2";
                            //alert("FLAG1 :" + processed);
			
                            if(processed){
                                //alert(4);
                                dlgParams = {};
                                dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                                dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                                dlgParams.event = event;
                                //event.originalEvent.options.submit = true;       
                                $('#frmMain_flagButton').val(flags.toString());
                                $("#btnCreate").unsubscribe("beforeSubmitCasa");
                                $("#btnCreate").click();
                                $("#btnCreate").attr("disabled","disabled");
                                $("#spinner").removeClass("ui-helper-hidden");
                                waitingMessage(3,messaging,"divwaitingRespond");
                            }
                        },function(){});
                    });
                } else if(existingCif == 'true' && processed== true && hrFlag != '0000'){
                    messageBoxParam('divInnerMessage',3, realMessage, function(){
                        messageBoxOkCancel(1, 'This Is Existing Customer Press "OK" to Continue ',function(event){
                            var flags = "2";
                            //alert("FLAG1 :" + processed);
			
                            if(processed){			
                                //alert(4);
                                dlgParams = {};
                                dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                                dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                                dlgParams.event = event;
                                //event.originalEvent.options.submit = true;       
                                $('#frmMain_flagButton').val(flags.toString());
                                $("#btnCreate").unsubscribe("beforeSubmitCasa");
                                $("#btnCreate").click();
                                $("#btnCreate").attr("disabled","disabled");
                                $("#spinner").removeClass("ui-helper-hidden");
                                waitingMessage(3,messaging,"divwaitingRespond");
                            }});
                    });
                }
                //alert(4);
                flagAuth = "N";
            });
            </script>
</s:if>