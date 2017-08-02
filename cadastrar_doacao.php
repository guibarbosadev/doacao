<?php

$con = mysqli_connect('mysql.hostinger.com.br','u129943523_detec','123456','u129943523_detec');
$serie = $_GET['cd_serie'];
$marca = $_GET['nm_marca'];
$modelo = $_GET['nm_modelo'];
$endereco = $_GET['ds_endereco'];
$telefone1 = $_GET['cd_telefone1'];
$nome = $_GET['nm_doador'];
$resposta = array();
if(!empty($serie) && !empty($marca) && !empty($modelo) && !empty($endereco) && !empty($telefone1) && !empty($nome))
{
	$result = mysqli_query($con, "SELECT nm_modelo FROM tb_doacao WHERE cd_serie = '$serie'");
	if(mysqli_num_rows($result) == 0)
	{
		$telefone2 = $_GET['cd_telefone2'];
		if(!empty($telefone2))
		{
			$sql = mysqli_query($con,"INSERT INTO tb_doacao(cd_serie,nm_marca,nm_modelo,ds_endereco,cd_telefone1,cd_telefone2,nm_doador) VALUES ('$serie','$marca','$modelo','$endereco','$telefone1','$telefone2','$nome')");
			if($sql)
			{
				$resposta['resultado'] = "sim";
			}
			else
			{
				$resposta['resultado'] = "nao1";
			}
		}
		else
		{
			$sql = mysqli_query($con,"INSERT INTO tb_doacao(cd_serie,nm_marca,nm_modelo,ds_endereco,cd_telefone1,nm_doador) VALUES ('$serie','$marca','$modelo','$endereco','$telefone1','$nome')");
			if($sql)
			{
				$resposta['resultado'] = "sim";
			}
			else
			{
				$resposta['resultado'] = "nao1";
			}
		}
	}
	else
	{
		$resposta['resultado'] = "existe";
	}
}
else
{
	$resposta['resultado'] = "nao3";
}
echo json_encode($resposta);
mysqli_close($con);
?>