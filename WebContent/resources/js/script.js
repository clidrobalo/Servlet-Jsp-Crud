
function validarCampos() {

}

//limpar compos cep
function limparCamposCep() {
	$("#rua").val("");
    $("#bairro").val("");
    $("#cidade").val("");
    $("#uf").val("");
    $("#ibge").val("");
}

function consultaCep() {
	var cep = $("#cep").val();
	
	//Nova variável "cep" somente com dígitos.
    cep = cep.replace(/\D/g, '');

    //Verifica se campo cep possui valor informado.
    if (cep != "") {

        //Expressão regular para validar o CEP.
        var validacep = /^[0-9]{8}$/;

        //Valida o formato do CEP.
        if(validacep.test(cep)) {

            //Consulta o webservice viacep.com.br/
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                if (!("erro" in dados)) {
                	$("#rua").val(dados.logradouro);
                    $("#bairro").val(dados.bairro);
                    $("#cidade").val(dados.localidade);
                    $("#uf").val(dados.uf);
                    $("#ibge").val(dados.ibge);
                } //end if.
                else {
                    //CEP pesquisado não foi encontrado.
                	limparCamposCep();
                    alert("CEP nao encontrado.");
                }
            });
        } //end if.
        else {
            //cep é inválido.
        	limparCamposCep();
            alert("Formato de CEP invalido.");
        }
    } //end if.
    else {
    	limparCamposCep();
        //cep sem valor, limpa formulário.
    }
}