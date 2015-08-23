<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'login-form',
	'enableClientValidation'=>false,
	'clientOptions'=>array(
	'validateOnSubmit'=>true,
	),
)); ?>

<?php echo $form->error($model,'username'); ?>
<?php echo $form->error($model,'password'); ?>
<?php echo $form->error($model,'rememberMe'); ?>

<table class="table_info">
	<tr>
		<td width="100px"><label for="LoginForm_username">Логин</label></td>
		<td><input size=20 type="text" id="LoginForm_username" name="LoginForm[username]" class="field" /></td>
	</tr>
	<tr>
		<td><label for="LoginForm_password">Пароль</label> <a href="<?php echo Yii::app()->homeUrl; ?>lostpassword" title="Забыл пароль">?</a></td>
		<td><input size=20 type="password" id="LoginForm_password" name="LoginForm[password]" class="field" /></td>
	</tr>
	<tr>
		<td><input type="submit" value="Войти" name="yt1" class="button" /></td>
		<td align="right"><?php echo $form->checkBox($model,'rememberMe'); ?><?php echo $form->label($model,'rememberMe'); ?></td>
	</tr>
</table>

<?php $this->endWidget(); ?>