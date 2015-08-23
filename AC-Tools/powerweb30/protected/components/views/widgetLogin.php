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
		<td width="100px"><label for="LoginForm_username">Username</label></td>
		<td><input size=15 type="text" id="LoginForm_username" name="LoginForm[username]"></td>
	</tr>
	<tr>
		<td><label for="LoginForm_password">Password</label> <a href="<?php echo Yii::app()->homeUrl; ?>lostpassword" title="Forgot your password">?</a></td>
		<td><input size=15 type="password" id="LoginForm_password" name="LoginForm[password]"></td>
	</tr>
	<tr>
		<td><input type="submit" value="Log-in" name="yt1" class="button"></td>
		<td align="left"><?php echo $form->checkBox($model,'rememberMe'); ?><?php echo $form->label($model,'rememberMe'); ?></td>
	</tr>
</table>

<?php $this->endWidget(); ?>

<input type="button" ONCLICK="location.href='<?php echo Yii::app()->homeUrl; ?>register'" class="link_button" value="Register an account" />