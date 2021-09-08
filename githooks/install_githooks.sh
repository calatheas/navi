#!/bin/sh

root_path=$(git rev-parse --show-toplevel)

ln -fs ${root_path}/githooks/pre-commit ${root_path}/.git/hooks/pre-commit
