#pragma once


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <stdint.h>

#define DISC_PAGE_SIZE

typedef struct 
{
    uint8_t status;
    int64_t root;
    int64_t prox_rrn;
    int32_t num_chaves;
} btree;

typedef struct
{
    int32_t height;
    int32_t num_keys;

    void* data;
} btree_node;
