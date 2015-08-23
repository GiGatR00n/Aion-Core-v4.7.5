<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Передача поинтов</div>
		<table class="items">
			<tr>
				<th>Отправитель</th>
				<th>Получатель</th>
				<th>Сумма</th>
				<th>Дата</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data->sender; ?></td>
				<td><?php echo $data->recipient; ?></td>
				<td><?php echo $data->sum; ?></td>
				<td width="180px"><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>