function generatePDF(billType,ntpn) {
             if (window.ActiveXObject || "ActiveXObject" in window) { // IE
                     generatePDF().save("MPNPayment.pdf");
             }
             else {
                       var newWindow = window.open('', 'Print_' + new Date(), 'width=800, height=600');
                       var frame = newWindow.document.createElement('iframe');
                       frame.id = 'fraPrint';
                       frame.width = '100%';
                       frame.height = '100%';
                       newWindow.document.body.appendChild(frame);
                       frame.src = newWindow.opener.generatePDFContent(billType,ntpn).toDataUri();
             }
     }

function generatePDFContent(billType,ntpn) {
    var billingType = billType;
    var ntpn = ntpn;
    var string0;
    var string1;
    var string2;
    var string3;
    var string4;
    var string5;
    var string6;
    var string7;
    var string8;
    var string9;
    var string10;
    var string12;
    var string13;
    var string14;
    var string15;
    var string16;

    var val1;
    var val2;
    var val3;
    var val4;
    var val5;
    var val6;
    var val7;
    var val8;
    var val9;
    var val10;
    var val12;
    var val13;
    var val14;
    var val15;
    var val16;
    if(billingType == 'DJA'){
        string0 = "Penerimaan Negara Bukan Pajak";
        string1 = "Jumlah Setoran                      : ";
        string2 = "Terbilang                                : ";
        string14 = "Kementerian/lembaga            : ";
        string15 = "Unit Eselon I                           : ";
        string16 = "Satuan Kerja                          : ";
        val1 = $('#frmPrint_etaxPrint_jmlSetoran').val();
        val2 = $('#frmPrint_etaxPrint_terbilang').val();
        val14 = $('#frmPrint_etaxPrint_kl').val();
        val15 = $('#frmPrint_etaxPrint_unitEselon').val();
        val16 = $('#frmPrint_etaxPrint_satuanKerja').val();
    }else if(billingType == 'DJP'){
        string0 = "Penerimaan Pajak";
        string1 = "Alamat                                   : ";
        string2 = "Nomor Objek Pajak               : ";
        string3 = "Mata Anggaran                      : ";
        string4 = "Jenis Setoran                         : ";
        string5 = "Masa Pajak                            : ";
        string6 = "Nomor Ketetapan                   : ";
        string7 = "Jumlah Setoran                      : ";
        string8 = "Mata Uang  : ";
        string9 = "Terbilang                                : ";
        string12= "NPWP                                    : ";
        val1 = $('#frmPrint_etaxPrint_wpAddress').val();
        val2 = $('#frmPrint_etaxPrint_nop').val();
        val3 = $('#frmPrint_etaxPrint_mataAnggaran').val();
        val4 = $('#frmPrint_etaxPrint_jenisSetoran').val();
        val5 = $('#frmPrint_etaxPrint_masaPajak').val();
        val6 = $('#frmPrint_etaxPrint_noKetetapan').val();
        val7 = $('#frmPrint_etaxPrint_jmlSetoran').val();
        val8 = $('#frmPrint_etaxPrint_mataUang').val();
        val9 = $('#frmPrint_etaxPrint_terbilang').val();
        val12 = $('#frmPrint_etaxPrint_npwp').val();
    }else{
        string0 = "Penerimaan Bea Dan Cukai ";
        string3 = "Jenis Dokumen                      : ";
        string4 = "Nomor Dokumen                    : ";
        string5 = "Tanggal Dokumen                  : ";
        string6 = "Jumlah Setoran                      : ";
        string7 = "Terbilang                                : " ;
        string10 = "Mata Uang  : ";
        string12= "Id Wajib Pajak                        : ";
        string13= "Kode KPPBC                          : ";
        val3 = $('#frmPrint_etaxPrint_jenisDokumen').val();
        val4 = $('#frmPrint_etaxPrint_nomorDokumen').val();
        val5 = $('#frmPrint_etaxPrint_tglDokumen').val();
        val6 = $('#frmPrint_etaxPrint_jmlSetoran').val();
        val7 = $('#frmPrint_etaxPrint_terbilang').val();
        val10 = $('#frmPrint_etaxPrint_mataUang').val();
        val12 = $('#frmPrint_etaxPrint_npwp').val();
        val13 = $('#frmPrint_etaxPrint_kodeKppbc').val();
    }
    var MyDoc = FPDF.Doc.extend({
            Page: FPDF.Page.extend({
                    defaultCss: {
                            padding:[10,10,10,10],
                            lineHeight:1.4
                    }
            })
    });
    var doc = new MyDoc({format:'a4'});
    doc.css({});
    doc.title("MPN");
    doc.author("Bank Danamon Supplementary Module (BDSM)");
    var pages = [""];
    var onePage = function(index) {
            var nowDate = new Date();
            var obj 
                    = 
                        FPDF('div')
                            .append(
                                            FPDF('flexbox')
                                                    .append(
                                                            FPDF('div')
                                                                    .css({paddingTop: 5, width: 62, height: 17, borderWidth: 0.3 })
                                                                    .append(FPDFNewImage("data:image/png;base64,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",59, 12))
                                                    )
                                                    .append(
                                                            FPDF('div')
                                                                    .css({width: 82, height: 15, borderWidth: 0.3})
                                                             .append(
                                                                FPDF('div')
                                                                        .text("BUKTI PENERIMAAN NEGARA")
                                                                        .css({
                                                                                fontSize: 12,
                                                                                marginTop :5,
                                                                                textAlign: 'center'
                                                                        })
                                                                .append(
                                                                        FPDF('div')
                                                                                .text(string0)
                                                                                .css({
                                                                                        fontSize: 12,
                                                                                        marginTop :5,
                                                                                        textAlign: 'center'
                                                                                })
                                                                        )
                                                                )
                                                    )
                                                    .append(
                                                            FPDF('div')
                                                                    .css({paddingTop: 5, textAlign: 'right', width: 50, height: 17, borderWidth: 0.3})
                                                                    .append(
                                                                        FPDF('div')
                                                                       .text("Kementerian Keuangan")
                                                                       .css({
                                                                               fontSize: 11,
                                                                               textAlign: 'center',
                                                                               paddingTop: 3
                                                                       })
                                                                  )  
                                                    )
                                    )
                            .append(
                                    FPDF('div')
                                            .text("Data Pembayaran:")
                                            .css({
                                                    fontSize: 11,
                                                    textAlign: 'left',
                                                    borderWidth: 0.3,
                                                    padding:1,
                                                    paddingTop:5
                                            })
                                    )
                             .append(
                                    FPDF('div')
                                            .css({
                                                    borderWidth: 0.3,
                                                    padding:1
                                            })

                                          .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text("Tanggal dan Jam Bayar        : "+$('#frmPrint_etaxPrint_tglBayar').val())
                                                )
                                                .append(
                                                        FPDF('div')
                                                                .text("                 NTB/NTP           : "+$('#frmPrint_etaxPrint_ntb').val())
                                                )
                                              )

                                            .append(
                                                FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                    FPDF('div')
                                                        .css({textAlign: 'left'})
                                                        .text("Tanggal Buku                        : "+$('#frmPrint_etaxPrint_tglBuku').val())
                                                )
                                                .append(
                                                        FPDF('div')
                                                                .text("                 NTPN                 : "+$('#frmPrint_etaxPrint_ntpn').val())
                                                )
                                            )

                                          .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                    FPDF('div')
                                                        .css({textAlign: 'left'})
                                                        .text("Kode Cabang Bank               : "+$('#frmPrint_etaxPrint_kodeCabang').val())
                                                )
                                                .append(
                                                        FPDF('div')
                                                                .text("                 STAN                 : "+$('#frmPrint_etaxPrint_stan').val())
                                                )
                                             )
                                    )
                            .append(
                                    FPDF('div')
                                            .text("Data Setoran:")
                                            .css({
                                                    fontSize: 11,
                                                    textAlign: 'left',
                                                    borderWidth: 0.3,
                                                    padding:1,
                                                    paddingTop:5

                                            })
                                    )
                            .append(billingType != "DJBC" ?
                                    FPDF('div')
                                            .css({
                                                    borderWidth: 0.3,
                                                    padding:1

                                            })
                               .append(
                                       FPDF('flexbox')
                                           .css({fontSize: 11, lineHeight: 1.5})
                                           .append(
                                                   FPDF('div')
                                                           .css({textAlign: 'left'})
                                                           .text("Kode Billing                            : "+$('#frmPrint_etaxPrint_kodeBilling').val())
                                           )
                                       )

                              .append(
                                        FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            //.text("NPWP                                    : "+$('#frmPrint_etaxPrint_npwp').val())
                                                            .text(string12+val12)
                                                            
                                            )

                                     )  
                               .append(billingType == "DJP" ?
                                        FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            .text("Nama Wajib Pajak                 : "+$('#frmPrint_etaxPrint_wpName').val())
                                            ):
                                            FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            .text("Nama Wajib Bayar                 : "+$('#frmPrint_etaxPrint_wpName').val())
                                            )
                                        )
                               .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string14+val14)
                                                )
                                            )
                                .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string15+val15)
                                                )
                                            )
                                 .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string16+val16)
                                                )
                                            )
                               .append(billingType == "DJA" ?
                                        FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            .text(string1+val1)
                                            ):FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            .text(string1+val1)
                                            )
                                     )
                                .append(
                                        FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            .text(string2+val2)
                                            )
                                    )
                              : FPDF('div')
                                            .css({
                                                    borderWidth: 0.3,
                                                    padding:1

                                            })
                               .append(
                                       FPDF('flexbox')
                                           .css({fontSize: 11, lineHeight: 1.5})
                                           .append(
                                                   FPDF('div')
                                                           .css({textAlign: 'left'})
                                                           .text("Kode Billing                            : "+$('#frmPrint_etaxPrint_kodeBilling').val())
                                           )
                                       )

                              .append(
                                        FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            //.text("NPWP                                    : "+$('#frmPrint_etaxPrint_npwp').val())
                                                            .text(string12+val12)
                                                            
                                            )

                                     )  
                               .append(billingType == "DJP" ?
                                        FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            .text("Nama Wajib Pajak                 : "+$('#frmPrint_etaxPrint_wpName').val())
                                            ):
                                            FPDF('flexbox')
                                            .css({fontSize: 11, lineHeight: 1.5})
                                            .append(
                                                    FPDF('div')
                                                            .css({textAlign: 'left'})
                                                            .text("Nama Wajib Bayar                 : "+$('#frmPrint_etaxPrint_wpName').val())
                                            )
                                        )
                                   .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string3+val3)
                                                )
                                      )
                                      .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string4+val4)
                                                )
                                       )
                                       .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string5+val5)
                                                )
                                       )
                                       .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string13+val13)
                                                )
                                       )    
                                       .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string6+val6)
                                                )
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'center'})
                                                                .text(string10+val10)
                                                )
                                       )
                                       .append(billingType == "DJP" ?
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string7+val7)
                                                )
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'center'})
                                                                .text(string8+val8)
                                                ):FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string7+val7)
                                                )
                                       )
                              )
                             .append(billingType != "DJA" && billingType != "DJBC" ?
                                    FPDF('div')
                                            .text(billingType == "DJA" || billingType == "DJBC" ?"":"Rincian Data Setoran:")
                                            .css({
                                                    fontSize: 11,
                                                    lineHeight: 1.5,
                                                    textAlign: 'left',
                                                    borderWidth: 0.3,
                                                    padding:1
                                            }):""
                                    )
                              .append(billingType != "DJA" && billingType != "DJBC" ?
                                    FPDF('div')
                                            .css({
                                                    borderWidth: 0.3,
                                                    padding:1
                                            })
                                     .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string3+val3)
                                                )
                                      )
                                      .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string4+val4)
                                                )
                                       )
                                       .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string5+val5)
                                                )
                                       )
                                       .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string13)
                                                )
                                       )    
                                       .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string6+val6)
                                                )
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'center'})
                                                                .text(string10+val10)
                                                )
                                       )
                                       .append(billingType == "DJP" ?
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string7+val7)
                                                )
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'center'})
                                                                .text(string8+val8)
                                                ):FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string7+val7)
                                                )
                                       )
                                       .append(
                                            FPDF('flexbox')
                                                .css({fontSize: 11, lineHeight: 1.5})
                                                .append(
                                                        FPDF('div')
                                                                .css({textAlign: 'left'})
                                                                .text(string9+val9)
                                                )
                                       ):FPDF('div')
                                                .css({
                                                        borderWidth: 0.3,
                                                        padding:1

                                                })
                                                .text("This is a computer generated message and requires no signature")
                                        .append(
                                        FPDF('div')
                                                .text("Informasi ini hasil cetakan komputer dan tidak memerlukan tanda tangan")
                                      )

                                   )
                            .append(billingType != "DJA" && billingType != "DJBC"?
                                        FPDF('div')
                                                .css({
                                                        borderWidth: 0.3,
                                                        padding:1

                                                })
                                                .text("This is a computer generated message and requires no signature")
                                        .append(
                                        FPDF('div')
                                                .text("Informasi ini hasil cetakan komputer dan tidak memerlukan tanda tangan")
                                      ):""
                                    )
                             .append(ntpn.length == 0 ?
                                        FPDF('div')
                                            .text("Transaksi sedang dalam proses")
                                            :""
                                    );
            return obj;
    };
    doc.append(onePage());
    return doc;
}
var FPDFNewImage = function(dataUri, top, left, width, height) {
            var obj = 
                    FPDF('img')
                            .css({
                                    left: top,
                                    top: left,
                                    width: width,
                                    height: height
                            })
                            .setDataUri(dataUri);

            return obj;
    };

var FPDFNewImage = function(dataUri, width, height) {
        var obj = 
                FPDF('img')
                        .css({
                                width: width,
                                height: height,
                                marginLeft : 1
                        })
                        .setDataUri(dataUri);

        return obj;
};

var FPDFNewImageFromURL = function(url, type, width, height) {
        var obj = 
                FPDF('img')
                        .css({
                                width: width,
                                height: height
                        })
                        .setSrc(url, type);

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