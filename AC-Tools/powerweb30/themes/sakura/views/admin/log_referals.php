<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Список рефералов</div>
		<table class="items">
			<tr>
				<th>Мастер</th>
				<th>Реферал</th>
				<th>Дата</th>
				<th>Статус</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data->master; ?></td>
				<td><?php echo $data->slave; ?></td>
				<td width="150px"><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
				<td><?php echo Info::status($data->status); ?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>