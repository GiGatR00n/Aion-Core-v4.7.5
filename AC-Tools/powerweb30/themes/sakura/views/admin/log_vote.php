<script>
	$(function() {
		$( "#Clean_date" ).datepicker({
			numberOfMonths: 1,
			firstDay: 1,
			dateFormat: 'yy-mm-dd',
			showButtonPanel: false
		});
	});
</script>

<div class="note mb15">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Чистка логов</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<table class="items">
		<?php echo CHtml::beginForm(); ?>
			<tr>
				<td>Очистить до даты</td>
				<td><input type="text" id="Clean_date" name="Clean[date]" /></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Очистить"></td>
			</tr>
		<?php echo CHtml::endForm(); ?>
		</table>
	</div>
</div>

<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Голосования за сервер</div>
		<table class="items">
			<tr>
				<th>Логин</td>
				<th>Тип</td>
				<th>Дата</td>
				<th>Статус</td>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data->login; ?></td>
				<td><?php echo $data->type; ?></td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
				<td><?php echo $data->status; ?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>