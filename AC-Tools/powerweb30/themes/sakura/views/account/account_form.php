<script>
	$(function() {
		$( "#AccountData_expire" ).datepicker({
			numberOfMonths: 2,
			firstDay: 1,
			dateFormat: 'yy-mm-dd',
			showButtonPanel: false
		});
	});
</script>

<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Редактирование аккаунта</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($model)?></div>
		
		<table class="table_info">
			<tr>
				<td width="300px"><label for="AccountData_name">Имя</label></td>
				<td><?php echo CHtml::activeTextField($model,'name'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_email">Email</label></td>
				<td><?php echo CHtml::activeTextField($model,'email'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_access_level">Уровень доступа</label></td>
				<td><?php echo CHtml::activeTextField($model,'access_level'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_membership">Привилегии</label></td>
				<td><?php echo CHtml::activeTextField($model,'membership'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_expire">Дата окончания привилегий</label></td>
				<td><?php echo CHtml::activeTextField($model,'expire'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_donatemoney">Баланс</label></td>
				<td><?php echo CHtml::activeTextField($model,'donatemoney') ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_activated">Аккаунт активен</label></td>
				<td>
					<?php if ($model->activated == 1): ?>
						<input type="checkbox" id="AccountData_activated" name="AccountData[activated]" checked="checked">
					<?php else: ?>	
						<input type="checkbox" id="AccountData_activated" name="AccountData[activated]">
					<?php endif; ?>
				</td>
			</tr>
			<tr>
				<td><label for="AccountData_new_password">Новый пароль</label></td>
				<td><?php echo CHtml::activeTextField($model,'new_password') ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Сохранить"></td>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>