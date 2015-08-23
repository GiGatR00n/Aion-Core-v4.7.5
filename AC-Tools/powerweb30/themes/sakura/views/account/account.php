<div class="note mb15">
	<div class="note_body">
		<div class="note_title">Информация о аккаунте</div>
		<table class="items">
			<tr>
				<td width="300">Логин</td>
				<td><?php echo $model->name; ?></td>
			</tr>
			<tr>
				<td>E-mail</td>
				<td><?php echo $model->email; ?></td>
			</tr>
			<tr>
				<td>Реферальная ссылка</td>
				<td><?php echo $ref_link; ?></td>
			</tr>
			<tr>
				<td>Уровень доступа</td>
				<td><?php echo Info::access_level($model->access_level); ?></td>
			</tr>
			<tr>
				<td>Привилегии аккаунта</td>
				<td><?php echo Info::membership($model->membership); ?></td>
			</tr>
			<tr>
				<td>Истекают</td>
				<td><?php echo $model->expire; ?></td>
			</tr>
			<tr>
				<td>Последний IP</td>
				<td><?php echo $model->last_ip; ?></td>
			</tr>
			<tr>
				<td>Баланс</td>
				<td><b><?php echo $model->donatemoney; ?> Points</b></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<?php $this->widget('application.components.WidgetRobo'); ?>
					<?php $this->widget('application.components.WidgetIk'); ?>
				</td>
			</tr>
		</table>
	</div>
</div>

<div class="note">
	<div class="note_body">
		<div class="note_title">Передача поинтов</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
			<div class="form"><?php echo CHtml::errorSummary($form)?></div>
			<table class="h32">
				<tr>
					<td>
						<?php echo CHtml::activeLabel($form,'recipient')?>
						<?php echo CHtml::activeTextField($form,'recipient'); ?>
					</td>
					<td>
						<?php echo CHtml::activeLabel($form,'sum')?>
						<?php echo CHtml::activeTextField($form,'sum'); ?>
					</td>
					<td>
						<input type="submit" class="button" name="yt0" value="Передать">
					</td>
				</tr>
			</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>