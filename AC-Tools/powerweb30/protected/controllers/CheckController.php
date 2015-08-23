<?php







class CheckController extends Controller
{
	public $layout='//content';
	
	
	public function actionIndex()
	{
		echo '<title>Minimum server requirement checks</title>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<style>
			body {font: 10pt Verdana, sans-serif; color:#444; background: #EBEDF0;}
			table {font: 10pt Verdana, sans-serif; color:#444; margin: auto; border-spacing: 10px;}
			td {border: solid 1px #888; background: #FAFAFA; padding: 10px 20px;}
		</style>
		<table>
			<tr>
				<td>PHP version: <b>'.PHP_VERSION.'</b></td>
				<td>'.$this->php_check().'</td>
			</tr>
			<tr>
				<td>pdo_mysql extension check</td>
				<td>'.$this->pdo_mysql_check().'</td>
			</tr>
			<tr>
				<td>pdo_sqlite caching check</td>
				<td>'.$this->pdo_sqlite_check().'</td>
			</tr>
		</table>
		<br />';
		phpinfo();
	}
	
	
	public function php_check() {
		if (PHP_VERSION >= 5.2) return '<font color="green">Successful</font>';
		else return '<font color="red">Failed - Please upgrade to PHP version <b>5.2</b> or <b>5.3</b>.</font>';
	}
	
	public function pdo_mysql_check() {
		if (extension_loaded('pdo_mysql')) return '<font color="green">Successful</font>';
		else return '<font color="red">Failed - Please install the PHP extension <b>pdo_mysql</b>.</font>';
	}
	
	public  function pdo_sqlite_check() {
		if (extension_loaded('pdo_sqlite')) return '<font color="green">Successful</font>';
		else return '<font color="orange">Failed - Please install the PHP extension <b>pdo_sqlite</b>.</font>';
	}
}