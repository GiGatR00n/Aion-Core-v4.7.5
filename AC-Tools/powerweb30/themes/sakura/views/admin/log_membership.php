<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Покупка привилегий</div>
		<table class="items">
			<tr>
				<th>Аккаунт</th>
				<th>Название</th>
				<th>Тип</th>
				<th>Дни</th>
				<th>Цена</th>
				<th>Дата</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data->account; ?></td>
				<td><?php echo $data->title; ?></td>
				<td><?php echo $data->type; ?></td>
				<td><?php echo $data->duration; ?></td>
				<td><?php echo $data->price; ?></td>
				<td width="150px"><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>