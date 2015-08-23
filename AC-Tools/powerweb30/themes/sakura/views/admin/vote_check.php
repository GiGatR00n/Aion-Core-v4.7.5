<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Пересчет голосов</div>
		<table class="items">
			<tr>
				<th>ID</td>
				<th>Логин</td>
				<th>Тип</td>
				<th>Дата</td>
				<th>Статус</td>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data['id']; ?></td>
				<td><?php echo $data['login']; ?></td>
				<td><?php echo $data['type']; ?></td>
				<td><?php echo $data['date']; ?></td>
				<td><?php echo $data['message']; ?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>
