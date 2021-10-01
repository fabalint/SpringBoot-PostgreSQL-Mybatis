#!/bin/bash


image_id=2778ed223757

docker -D -l debug run \
		-p 5432:5432 \
		--env-file postgres.runlocal.env \
		$image_id

#		 --mount type=bind,source=/c/tmp/oam.log,target=/opt/liferay/agent/Agent_001/logs/debug \
#
#		-v '/c/DEV/tmp/qwertz.sh:/mnt/liferay/scripts/qwertz.sh' \
#
