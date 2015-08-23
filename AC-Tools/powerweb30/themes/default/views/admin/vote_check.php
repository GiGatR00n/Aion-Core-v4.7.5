






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Recount votes
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>ID</td>
				<th>Login</td>
				<th>Type</td>
				<th>Date</td>
				<th>Status</td>
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